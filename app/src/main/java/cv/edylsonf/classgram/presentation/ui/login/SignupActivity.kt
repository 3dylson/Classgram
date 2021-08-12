package cv.edylsonf.classgram.presentation.ui.login


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.DEFAULT_PROFILE_PIC
import cv.edylsonf.classgram.databinding.ActivitySignupBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "SignupActivity"

class SignupActivity : BaseActivity() {

    private lateinit var emailResendUser: FirebaseUser
    private lateinit var binding: ActivitySignupBinding

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setProgressBar(binding.progressBar2)

        database = Firebase.firestore
        auth = Firebase.auth

        animations()

        //Click listeners
        with(binding){
            signinBttn.setOnClickListener{ signUp() }
            backbtn.setOnClickListener { goBack()}
            resendEmailBtn.setOnClickListener { resendEmail() }
        }

    }

    private fun resendEmail() {
        emailResendUser.sendEmailVerification()
    }


    private fun animations(){

        //TODO Animate text fields

    }

    private fun goBack(){
        showProgressBar()
        onBackPressed()
        hideProgressBar()
        finish()
    }

    private fun signUp() {
        Log.d(TAG, "signUp")
        if (!validateForm()) {
            return
        }

        val password = binding.passwordReg.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (confirmPassword != password) {
            binding.confirmPassword.error = "Password don't match!"
            return
        }

        showProgressBar()
        val email = binding.signInEmailSentText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)
                hideProgressBar()

                if (task.isSuccessful) {
                    auth.signOut()
                    onAuthSuccess(task.result?.user!!)

                } else {
                    Log.e("Sign Up Failed: ", task.exception.toString())
                    Toast.makeText(this, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = user.email?.let { usernameFromEmail(it) }
        val name = binding.editTextTextPersonName.text.toString()

        val profilePicture = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(DEFAULT_PROFILE_PIC))
            .build()

        user.updateProfile(profilePicture)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Update default profile pic" + task.isSuccessful)
                }
            }

        emailResendUser = user
        binding.resendEmailBtn.visibility = View.VISIBLE
        user.sendEmailVerification()
        user.metadata?.let { writeNewUser(user.uid,username, name, user.email, it.creationTimestamp) }
        user.email?.let { showDialog(it) }
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

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "writeNewUser:onFailure: $exception")
            }

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
                //navToLogin()
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
        if (TextUtils.isEmpty(binding.signInEmailSentText.text.toString())) {
            binding.signInEmailSentText.error = "Required"
            result = false
        } else {
            binding.signInEmailSentText.error = null
        }

        if (TextUtils.isEmpty(binding.passwordReg.text.toString())) {
            binding.passwordReg.error = "Required"
            result = false
        } else {
            binding.passwordReg.error = null
        }

        if (TextUtils.isEmpty(binding.editTextTextPersonName.text.toString())) {
            binding.editTextTextPersonName.error = "Required"
            result = false
        } else {
            binding.editTextTextPersonName.error = null
        }

        if (TextUtils.isEmpty(binding.confirmPassword.text.toString())) {
            binding.confirmPassword.error = "Required"
            result = false
        } else {
            binding.confirmPassword.error = null
        }

        return result
    }

}