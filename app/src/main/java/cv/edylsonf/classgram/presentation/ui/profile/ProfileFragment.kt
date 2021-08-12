package cv.edylsonf.classgram.presentation.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentProfileBinding
import cv.edylsonf.classgram.domain.models.User


private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private var signedInUser: User? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        auth = Firebase.auth
        database = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        database.firestoreSettings = settings


        setup()

        with(binding){

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_action_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> appSettings()
            R.id.sign_out -> signOut()
        }
        return true
    }

    private fun appSettings() {
        TODO("Not yet implemented")
    }

    private fun setup() {
        // Something is going to be added here
    }

    //TODO make it a top menu action?
    private fun signOut() {
        Log.i(TAG,"User logging out pressed")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hope to see you soon...")
        builder.apply {
            setPositiveButton("Bye 👋") { _, _ ->
                auth.signOut()
            }
            setNegativeButton("Cancel") { _, _ ->
                Log.d(TAG, "Dialog cancelled")
            }
        }
        builder.create().show()

        //Because Fragment is not of Context type, we need to call the parent Activity
        /*val intent = Intent(activity,LoginActivity::class.java)
        activity?.startActivity(intent)*/
    }

    fun setSignedInUser(user: User) {
        this.signedInUser = user
    }


}