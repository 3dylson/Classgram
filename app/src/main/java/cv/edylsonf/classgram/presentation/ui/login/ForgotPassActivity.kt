package cv.edylsonf.classgram.presentation.ui.login

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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityForgotpassBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "ForgotPassActivity"

class ForgotPassActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotpassBinding
    private lateinit var view: View
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var emailReset: TextInputEditText
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpassBinding.inflate(layoutInflater)
        view = binding.root
        database = Firebase.firestore
        auth = Firebase.auth
        emailReset = binding.emailReset
        resetBtn = binding.resetPassbtn

        setContentView(view)
        setProgressBar(binding.progressBar)


        emailReset.addTextChangedListener(textWatcher)


        /*supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
        //setSupportActionBar(toolbar)

        animations()

        //Click listeners
        with(binding){
            resetPassbtn.setOnClickListener { sendPasswordReset() }

        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            resetBtn.isEnabled = validateForm()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun animations(){

        //TODO Animate text fields

    }


    private fun sendPasswordReset() {
        view.isEnabled = false
        hideKeyboard(view)
        resetBtn.isEnabled = false
        resetBtn.text = ""
        showProgressBar()

        val emailAddress = emailReset.text.toString()

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                    Log.d(TAG, "sendEmail:onComplete:" + task.isSuccessful)

                view.isEnabled = true
                hideProgressBar()
                resetBtn.text = getString(R.string.get_reset_email)
                resetBtn.isEnabled = true

                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Email sent",
                        Toast.LENGTH_SHORT).show()

                }
                else {

                    val builder = AlertDialog.Builder(this)
                    //builder.setTitle("Email Reset")
                    builder.setMessage(task.exception?.message)
                    builder.apply {
                        setPositiveButton("Try again") { _,_ ->

                        }
                    }
                    builder.create().show()
                }
            }
        // [END send_password_reset]
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(emailReset.text.toString())) {
            result = false
        }
        return result
    }


}