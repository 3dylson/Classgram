package cv.edylsonf.classgram.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.databinding.ActivityForgotpassBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "ForgotPassActivity"

class ForgotPassActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotpassBinding

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpassBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setProgressBar(binding.progressBar3)

        /*supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
        //setSupportActionBar(toolbar)

        database = Firebase.firestore
        auth = Firebase.auth

        animations()

        //Click listeners
        with(binding){
            backbtn2.setOnClickListener{ goBack() }
            resetPassbtn.setOnClickListener { sendPasswordReset() }

        }
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


    private fun sendPasswordReset() {
        // [START send_password_reset]
        Log.d(TAG, "sendEmail")
        if (!validateForm()) {
            return
        }

        showProgressBar()
        val emailAddress = binding.emailReset.text.toString()

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                    Log.d(TAG, "sendEmail:onComplete:" + task.isSuccessful)
                    hideProgressBar()

                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Email sent",
                        Toast.LENGTH_SHORT).show()

                }
            }
        // [END send_password_reset]
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(binding.emailReset.text.toString())) {
            binding.emailReset.error = "Required"
            result = false
        } else {
            binding.emailReset.error = null
        }

        return result
    }


}