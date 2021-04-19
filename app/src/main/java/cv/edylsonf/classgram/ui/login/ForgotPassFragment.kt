package cv.edylsonf.classgram.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentForgotpassBinding
import cv.edylsonf.classgram.ui.utils.BaseFragment

private const val TAG = "ForgotPassFragment"

class ForgotPassFragment : BaseFragment() {

    private var _binding: FragmentForgotpassBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View {
        _binding = FragmentForgotpassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.firestore
        auth = Firebase.auth

        setProgressBar(R.id.progressBar3)

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
        val intent = Intent(activity, LoginActivity::class.java)
        hideProgressBar()
        startActivity(intent)
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
                    Toast.makeText(context, "Email sent",
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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}