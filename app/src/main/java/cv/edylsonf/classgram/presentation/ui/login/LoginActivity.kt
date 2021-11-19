package cv.edylsonf.classgram.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.TRANSLATION_Y
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.ACTIVITY_REQUEST
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityLoginBinding
import cv.edylsonf.classgram.presentation.ui.MainActivity
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity
import cv.edylsonf.classgram.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val TAG = "LoginActivity"
private const val REQUEST_SIGN_IN = 12345

@AndroidEntryPoint
class LoginActivity : BaseActivity(), FirebaseAuth.AuthStateListener  {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var view: View
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var verifiedByProvider: Boolean = false

    private lateinit var appLogo: ImageView
    private lateinit var emailTextInput: TextInputEditText
    private lateinit var passwordTextInput: TextInputEditText
    private lateinit var logInBtn: Button

    private lateinit var networkUtils: NetworkUtils

    /*@Inject
    lateinit var connectivityManager: ConnectivityManager*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingUi()
        setup()
        setLogoTheme()
        animations()
        googleSignUp()

        // Click listeners
        with(binding) {
            login.setOnClickListener { signIn() }
            signup.setOnClickListener{ goSignUp()}
            resetPass.setOnClickListener{ resetPass() }
        }
    }

    private fun bindingUi() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        view = binding.root
        auth = Firebase.auth
        database = Firebase.firestore
        appLogo = binding.ivLogo
        emailTextInput = binding.email
        passwordTextInput = binding.password
        logInBtn = binding.login
    }

    private fun setup() {
        setContentView(view)
        setProgressBar(binding.progressBar)
        emailTextInput.addTextChangedListener(textWatcher)
        passwordTextInput.addTextChangedListener(textWatcher)
        networkUtils = NetworkUtils(applicationContext)
    }


    private fun Context.isDarkTheme(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            logInBtn.isEnabled = validateForm()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }


    private fun animations() {
        val google = findViewById<SignInButton>(R.id.google_signin)
        TRANSLATION_Y.set(google, 300F)
        google.alpha = 0F
        google.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
    }


    private fun goSignUp(){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }


    private fun resetPass(){
        val intent = Intent(this, ForgotPassActivity::class.java)
        startActivity(intent)
    }


    //Start Google Sign in Region
    private fun googleSignUp() {
        findViewById<SignInButton>(R.id.google_signin).setOnClickListener {
            openPostActivityCustom.launch(REQUEST_SIGN_IN)
        }

        auth.addAuthStateListener(this)

    }


    private fun createUserFromProvider(firebaseAuth: FirebaseAuth) {
        val user = hashMapOf(
            "uid" to firebaseAuth.currentUser?.uid,
            "username" to firebaseAuth.currentUser?.email.toString().split("@".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()[0],
            "headline" to null,
            "firstLastName" to firebaseAuth.currentUser?.displayName,
            "email" to firebaseAuth.currentUser?.email,
            "phone" to firebaseAuth.currentUser?.phoneNumber,
            "nationality" to null,
            "education" to null,
            "profilePic" to firebaseAuth.currentUser?.photoUrl.toString(),
            "answers" to 0,
            "emailVerified" to true,
            "dateCreated" to (firebaseAuth.currentUser?.metadata?.creationTimestamp
                ?: System.currentTimeMillis())
        )
        val address = hashMapOf(
            "city" to null,
            "country" to null
        )

        firebaseAuth.currentUser?.uid?.let { uid ->
            database.collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "User added with ID: ${this.uid}")
                    firebaseAuth.currentUser?.uid!!.let {
                        database
                            .collection("users").document(it)
                            .collection("addresses").document("college")
                    }.set(address)
                    hideProgressBar()
                    navToMain()
                }
                .addOnFailureListener { exception ->
                    hideProgressBar()
                    Log.w(TAG, "writeNewUser:onFailure: $exception")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage(exception.message)
                    builder.apply {
                        setPositiveButton("Try again") { _,_ ->

                        }
                    }
                    builder.create().show()
                }
        }
    }


    //TODO check how the metadata will be..
    private fun updateUser() {
        val userRef = database.collection("users").document(uid)
        userRef
            .update("emailVerified", true)
            .addOnSuccessListener { Log.d(TAG, "User successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating user", e) }
    }


    private val openPostActivityCustom =
        registerForActivityResult(ActivityContract()) { result ->
            if (result == true) {
                Timber.d("User signed in")
                verifiedByProvider = true
            } else {
                //TODO check if is network issue
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("An unknown network error has occurred.")
                builder.apply {
                    setPositiveButton("Dismiss") { _,_ ->

                    }
                }
                builder.create().show()
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
        if (!networkUtils.hasNetworkConnection()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Please check your network connection")
            builder.apply {
                setPositiveButton("Dismiss") { _,_ ->
                }
            }
            builder.create().show()
        } else {
            view.isEnabled = false
            hideKeyboard(view)
            logInBtn.isEnabled = false
            logInBtn.text = ""
            showProgressBar()

            val email = emailTextInput.text.toString()
            val password = passwordTextInput.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signIn:onComplete:" + task.isSuccessful)

                    view.isEnabled = true
                    hideProgressBar()
                    logInBtn.text = getString(R.string.login)
                    logInBtn.isEnabled = true

                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (!user!!.isEmailVerified) {
                            //TODO Change to snackbar dialog
                            Toast.makeText(this, "Verify your email!",
                                Toast.LENGTH_SHORT).show()
                            auth.signOut()
                        } else {
                            Toast.makeText(this, "Logged In",
                                Toast.LENGTH_SHORT).show()
                            navToMain()
                        }
                    } else {
                        Log.e(TAG,"signInWithEmail failed",task.exception)
                        val builder = AlertDialog.Builder(this)
                        //builder.setTitle("Log in")
                        builder.setMessage(task.exception?.message)
                        builder.apply {
                            setPositiveButton("Try again") { _,_ ->

                            }
                        }
                        builder.create().show()
                    }
                }

        }
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(emailTextInput.text.toString())) {
            result = false
        }

        if (TextUtils.isEmpty(passwordTextInput.text.toString())) {
            result = false
        }

        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.removeAuthStateListener(this)

    }

    override fun onResume() {
        super.onResume()
        setLogoTheme()
    }

    private fun setLogoTheme() {
        if (isDarkTheme()) appLogo.setImageResource(R.drawable.classgram_logo_white)
        else appLogo.setImageResource(R.drawable.classgram_logo)
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        Log.d(TAG, "AuthStateListener triggered. User: ${firebaseAuth.currentUser}")
        if (firebaseAuth.currentUser != null) {
            uid = firebaseAuth.currentUser!!.uid
            if (firebaseAuth.currentUser!!.isEmailVerified || verifiedByProvider) {
                database.collection("users")
                    .document(firebaseAuth.currentUser!!.uid)
                    .get().addOnCompleteListener(this) { userDoc ->
                        if (userDoc.result!!.exists()) {
                            if (!(userDoc.result!!.get("emailVerified") as Boolean)) {
                                //TODO Consider doing through cloud function
                                Log.d(TAG,"db User emailVerified returned false")
                                updateUser()
                            }
                            navToMain()
                        } else {
                            showProgressBar()
                            createUserFromProvider(firebaseAuth)
                        }
                    }
                //onEmailVerificationSuccess(it.currentUser)
            }
        }
    }

    private fun navToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}