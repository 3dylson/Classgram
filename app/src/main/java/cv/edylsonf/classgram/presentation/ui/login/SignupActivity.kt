package cv.edylsonf.classgram.presentation.ui.login


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
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
                    Toast.makeText(this, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = usernameFromEmail(user.email!!)
        val uid = user.uid
        // Write new user
        writeNewUser(uid, username, user.email)

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
    private fun writeNewUser(uid: String, username: String, email: String?) {
        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
            "email" to email
        )
        database.collection("users")
        .document(uid)
        .set(user)
        .addOnSuccessListener { documentReference -> 
            Log.d(TAG, "writeNewUser:onSuccess: " + documentReference.getId())
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "writeNewUser:onFailure: " + exception)
        }


}