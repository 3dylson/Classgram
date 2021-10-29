package cv.edylsonf.classgram.presentation.ui.login


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.DEFAULT_PROFILE_PIC
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivitySignupBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "SignupActivity"

class SignupActivity : BaseActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var view: View
    private lateinit var auth: FirebaseAuth
    private lateinit var emailResendUser: FirebaseUser
    private var accCreated: Boolean = false

    private lateinit var  editTextPersonName: TextInputEditText
    private lateinit var  signInEmailText: TextInputEditText
    private lateinit var  passwordReg: TextInputEditText
    private lateinit var  confirmPassword: TextInputEditText
    private lateinit var  signinBttn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingUi()
        setup()


        /*if (auth.currentUser?.isEmailVerified == false) {
            //binding.resendEmailBtn.visibility = View.VISIBLE
        }*/

        animations()

        //Click listeners
        with(binding){
            signinBttn.setOnClickListener{ signUp() }
            //resendEmailBtn.setOnClickListener { resendEmail() }
        }

    }

    private fun setup() {
        setContentView(view)
        setProgressBar(binding.progressBar)
        editTextPersonName.addTextChangedListener(textWatcher)
        signInEmailText.addTextChangedListener(textWatcher)
        passwordReg.addTextChangedListener(textWatcher)
        confirmPassword.addTextChangedListener(textWatcher)
    }

    private fun bindingUi() {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        view = binding.root
        auth = Firebase.auth
        database = Firebase.firestore
        editTextPersonName = binding.editTextTextPersonName
        signInEmailText = binding.signInEmailSentText
        passwordReg = binding.passwordReg
        confirmPassword = binding.confirmPassword
        signinBttn = binding.signinBttn
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            signinBttn.isEnabled = validateForm()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun resendEmail(){
        auth.currentUser?.sendEmailVerification()
        Toast.makeText(this, "Email Sent!",
            Toast.LENGTH_SHORT).show()

    }

    /*override fun onResume() {
        super.onResume()
        if (accCreated) {
            binding.resendEmailBtn.visibility = View.VISIBLE
        }
    }*/


    private fun animations(){

        //TODO Animate text fields

    }


    private fun signUp() {
        val password = passwordReg.text.toString()
        val confirmPassword = confirmPassword.text.toString()
        if (confirmPassword != password) {
            binding.confirmPassword.error = "Password don't match!"
            return
        }
        else {
            binding.confirmPassword.error = null
        }

        view.isEnabled = false
        hideKeyboard(view)
        signinBttn.isEnabled = false
        signinBttn.text = ""
        showProgressBar()

        val email = signInEmailText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)

                if (task.isSuccessful) {
                    //auth.signOut()
                    accCreated = true
                    onAuthSuccess(task.result?.user!!)

                } else {
                    enableSignInBtn()
                    //TODO Change to snackbar dialog
                    Log.e("Sign Up Failed: ", task.exception.toString())
                    Toast.makeText(this, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = user.email?.let { usernameFromEmail(it) }
        val name = editTextPersonName.text.toString()

        /*val profilePicture = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PIC))
            .build()

        user.updateProfile(profilePicture)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Update default profile pic" + task.isSuccessful)
                }
            }*/

        emailResendUser = user
        user.metadata?.let { writeNewUser(user.uid,username, name, user.email, it.creationTimestamp) }
        user.sendEmailVerification()
        //user.email?.let { showDialog(it) }
    }

    //TODO check how the metadata will be..
    private fun writeNewUser(uid: String, username: String?, name:String, email: String?, creationTimestamp: Long) {
        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
            "headLine" to null,
            "firstLastName" to name,
            "email" to email,
            "phone" to null,
            "nationality" to null,
            "education" to null,
            "profilePic" to DEFAULT_PROFILE_PIC,
            "answers" to 0,
            "emailVerified" to false,
            "dateCreated" to creationTimestamp,
        )
        val address = hashMapOf(
            "city" to null,
            "country" to null
        )
        /*val connection = HashMap<String, Any>()
        val posts = HashMap<String, Any>()*/

        val addressRef = database
            .collection("users").document(uid)
            .collection("addresses").document("college")

        /*val connectionsRef = database
            .collection("users").document(uid)
            .collection("connections")

        val postsRef = database
            .collection("users").document(uid)
            .collection("posts")*/

        database.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG,"User added with ID: $uid")
                addressRef.set(address)
                /*connectionsRef.add(connection)
                postsRef.add(posts)*/
                //binding.resendEmailBtn.visibility = View.VISIBLE

                enableSignInBtn()
                showDialog(email!!)

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "writeNewUser:onFailure: $exception")
                enableSignInBtn()
            }

    }

    private fun enableSignInBtn() {
        view.isEnabled = true
        hideProgressBar()
        signinBttn.text = getString(R.string.sign_up)
        signinBttn.isEnabled = true
    }

    private fun usernameFromEmail(email: String): String {
        return if (email.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun showDialog(email: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Email Confirmation")
        builder.setMessage("We need to verify your email address. Link sent to the email provided:\n$email")
        builder.apply {
            setPositiveButton("Ok") { _,_ ->
                auth.signOut()
                navToLogin()
            }
        }
        builder.create().show()
    }

    private fun navToLogin() {
        //Later can pass user metadata
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(signInEmailText.text.toString())) {
            result = false
        }

        if (TextUtils.isEmpty(passwordReg.text.toString())) {
            result = false
        }

        if (TextUtils.isEmpty(editTextPersonName.text.toString())) {
            result = false
        }

        if (TextUtils.isEmpty(confirmPassword.text.toString())) {
            result = false
        }
        return result
    }

}