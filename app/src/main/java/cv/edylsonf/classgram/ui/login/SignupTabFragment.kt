package cv.edylsonf.classgram.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.data.model.User
import cv.edylsonf.classgram.databinding.SignupTabFragmentBinding
import cv.edylsonf.classgram.ui.utils.BaseFragment

private const val TAG = "SignupTabFragment"

class SignupTabFragment : BaseFragment() {

    private var _binding: SignupTabFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //TODO Animate text fields
        _binding = SignupTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.firestore
        auth = Firebase.auth

        setProgressBar(R.id.progress_circular)

        //Click listeners
        with(binding){
            signinBttn.setOnClickListener{ signUp() }
        }

    }

    override fun onStart() {
        super.onStart()

        // Check auth on Fragment start
        auth.currentUser?.let {
            onAuthSuccess(it)
        }
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
            .addOnCompleteListener(requireActivity()) { task ->
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)
                hideProgressBar()

                if (task.isSuccessful) {
                    onAuthSuccess(task.result?.user!!)
                } else {
                    Toast.makeText(context, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = usernameFromEmail(user.email!!)

        // Write new user
        writeNewUser(user.uid, username, user.email)

        // Go to MainFragment
        //findNavController().navigate(R.id.action_signupTabFragment_to_mainActivity)

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

    private fun writeNewUser(userId: String, name: String, email: String?) {
        val user = User(name, email)
        database.collection("users").add(user)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}