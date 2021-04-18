package cv.edylsonf.classgram.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View.TRANSLATION_Y
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.ACTIVITY_REQUEST
import cv.edylsonf.classgram.EXTRA_EMAIL
import cv.edylsonf.classgram.MainActivity
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityLoginBinding
import cv.edylsonf.classgram.ui.utils.BaseActivity

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
        //setSupportActionBar(toolbar)
        /*supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.setDisplayShowHomeEnabled(true)*/

        // Initialize Firebase Auth
        auth = Firebase.auth

        animations()
        googleSignUp()

        // Click listeners
        with(binding) {
            login.setOnClickListener { signIn() }
        }
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
           //TODO nav to home
        }
    }


    private fun animations() {

        val google = findViewById<SignInButton>(R.id.google_signin)

        TRANSLATION_Y.set(google, 300F)

        google.alpha = 0F

        google.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()


    }


    //Start Google Sign in Region
    private fun googleSignUp() {
        findViewById<SignInButton>(R.id.google_signin).setOnClickListener {
            openPostActivityCustom.launch(REQUEST_SIGN_IN)
        }

        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d(TAG, "AuthStateListener triggered. User: ${it.currentUser}")
            if (it.currentUser != null) {
                val email = it.currentUser?.email
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(EXTRA_EMAIL, email)
                startActivity(intent)
                finish()
            }
        }

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
        if (!validateForm()) {
            return
        }

        showProgressBar()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful)
                hideProgressBar()

                if (task.isSuccessful) {
                    onAuthSuccess(task.result?.user!!)
                    //TODO check if is necessary to get a nav here if is duplicating user
                } else {
                    Toast.makeText(this, "Sign In Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {

        val username = usernameFromEmail(user.email!!)

        // Write new user
        writeNewUser(user.uid, username, user.email)

        //TODO Go to MainFragment
        // findNavController().navigate(R.id.action_SignInFragment_to_MainFragment)
    }

    private fun usernameFromEmail(email: String): String {
        return if (email.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
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

    private fun writeNewUser(userId: String, username: String, email: String?) {
        val user = hashMapOf(
            "username" to username,
            "email" to email
        )
        database.collection("users").document(userId).set(user)

    }

    //TODO Create new activity for pass reset
    private fun sendPasswordReset() {
        // [START send_password_reset]
        val emailAddress = "user@example.com"

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
        // [END send_password_reset]
    }




}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}