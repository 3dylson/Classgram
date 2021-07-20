package cv.edylsonf.classgram.presentation.ui.login


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.databinding.ActivitySignupBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "SignupActivity"

class SignupActivity : BaseActivity() {

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
        }

    }


    private fun animations(){

        //TODO Animate text fields

    }

    private fun goBack(){
        showProgressBar()
        onBackPressed()
        hideProgressBar()
    }

    private fun signUp() {
        Log.d(TAG, "signUp")
        if (!validateForm()) {
            return
        }

        showProgressBar()
        val email = binding.signInEmailSentText.text.toString()
        val password = binding.passwordReg.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)
                hideProgressBar()

                if (task.isSuccessful) {
                    onAuthSuccess(task.result?.user!!)
                } else {
                    Log.e("Sign Up Failed: ", task.exception.toString())
                    Toast.makeText(this, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        user.sendEmailVerification()
        val username = usernameFromEmail(user.email!!)

        // Write new user
        user.metadata?.let { writeNewUser(user.uid, username, user.email, it.creationTimestamp) }


        finish()

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

        return result
    }

    //TODO check how the metadata will be..
    private fun writeNewUser(uid: String, username: String, email: String?, creationTimestamp: Long) {
        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
            "email" to email,
            "dateCreated" to creationTimestamp
        )
        database.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG,"User added with ID: $uid")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "writeNewUser:onFailure: $exception")
            }

    }


}