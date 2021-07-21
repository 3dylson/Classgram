package cv.edylsonf.classgram.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View.TRANSLATION_Y
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.ACTIVITY_REQUEST
import cv.edylsonf.classgram.EXTRA_EMAIL
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityLoginBinding
import cv.edylsonf.classgram.presentation.ui.MainActivity
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "LoginActivity"
private const val REQUEST_SIGN_IN = 12345

class LoginActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setProgressBar(binding.progressBar)



        // Initialize Firebase Auth
        auth = Firebase.auth
        database = Firebase.firestore

        animations()
        googleSignUp()

        // Click listeners
        with(binding) {
            login.setOnClickListener { signIn() }
            signup.setOnClickListener{ goSignUp()}
            resetPass.setOnClickListener{ resetPass() }
        }
    }



    private fun animations() {

        val google = findViewById<SignInButton>(R.id.google_signin)

        TRANSLATION_Y.set(google, 300F)

        google.alpha = 0F

        google.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()


    }

    private fun goSignUp(){
        showProgressBar()
        val intent = Intent(this, SignupActivity::class.java)
        hideProgressBar()
        startActivity(intent)
    }

    private fun resetPass(){
        showProgressBar()
        val intent = Intent(this, ForgotPassActivity::class.java)
        hideProgressBar()
        startActivity(intent)
    }


    //Start Google Sign in Region
    private fun googleSignUp() {
        findViewById<SignInButton>(R.id.google_signin).setOnClickListener {
            openPostActivityCustom.launch(REQUEST_SIGN_IN)
        }

        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d(TAG, "AuthStateListener triggered. User: ${it.currentUser}")
            if (it.currentUser != null && it.currentUser!!.isEmailVerified) {
                database.collection("users")
                    .document(it.currentUser!!.uid)
                    .get().addOnSuccessListener { userDoc ->
                        if (!(userDoc.get("emailVerified") as Boolean)) {
                            onEmailVerficationSuccess(it.currentUser)
                        }
                    }
                //onEmailVerficationSuccess(it.currentUser)
                val email = it.currentUser?.email
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(EXTRA_EMAIL, email)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun onEmailVerficationSuccess(user: FirebaseUser?) {
        updateUser(user!!.uid, user.isEmailVerified)
    }

    //TODO check how the metadata will be..
    private fun updateUser(uid: String, emailVerified: Boolean ) {
        val userRef = database.collection("users").document(uid)
        userRef
            .update("emailVerified", emailVerified)
            .addOnSuccessListener { Log.d(TAG, "User successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating user", e) }
    }

    private val openPostActivityCustom =
        registerForActivityResult(ActivityContract()) { result ->
            if (result != null) {
                Log.d(TAG, "User signed in")
            }
        }

    class ActivityContract : ActivityResultContract<Int, Boolean?>() {

        override fun createIntent(context: Context, input: Int): Intent {
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build().apply {
                    putExtra(ACTIVITY_REQUEST, REQUEST_SIGN_IN)
                }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            val data = intent?.getStringExtra(ACTIVITY_REQUEST)
            return resultCode == Activity.RESULT_OK && data != null
        }
    }
    //End Google Sign in Region

    private fun signIn() {
        Log.d(TAG, "signIn")
        binding.login.isEnabled = false
        if (!validateForm()) {
            binding.login.isEnabled = true
            return
        }

        showProgressBar()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful)
                hideProgressBar()
                binding.login.isEnabled = true

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (!user!!.isEmailVerified) {
                        Toast.makeText(this, "Verify your email!",
                            Toast.LENGTH_SHORT).show()
                        auth.signOut()
                    } else {
                        Toast.makeText(this, "Successfully logged in!",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG,"signInWithEmail failed",task.exception)
                    Toast.makeText(this, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(binding.email.text.toString())) {
            binding.email.error = "Required"
            result = false
        } else {
            binding.email.error = null
        }

        if (TextUtils.isEmpty(binding.password.text.toString())) {
            binding.password.error = "Required"
            result = false
        } else {
            binding.password.error = null
        }

        return result
    }

}