package cv.edylsonf.classgram.presentation.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.databinding.FragmentProfileBinding


private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        auth = Firebase.auth
        //TODO make db persistance

        setup()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        with(binding){
            logout.setOnClickListener { signOut() }
        }

        return binding.root
    }

    private fun setup() {
        // Something is going to be added here
    }

    //TODO make it a top menu action and show app dialog to confirm logout
    private fun signOut() {
        Log.i(TAG,"User logging out")
        val logoutBtn = binding.logout
        logoutBtn.text = "👋🏾"
        auth.signOut()

        //Because Fragment is not of Context type, we need to call the parent Activity
        /*val intent = Intent(activity,LoginActivity::class.java)
        activity?.startActivity(intent)*/
    }


}