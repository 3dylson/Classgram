package cv.edylsonf.classgram.presentation.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

        signedInUser = activity?.intent?.getParcelableExtra("signedInUser")

        setHasOptionsMenu(true)

        auth = Firebase.auth
        database = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        database.firestoreSettings = settings


        setup()

        with(binding){
          editProfileBtn.setOnClickListener { editProfile()}
        }

        return binding.root
    }


    private fun editProfile() {
        TODO("Not yet implemented")
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
        readconnections()
        Glide.with(this)
            .load(signedInUser?.profilePic)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.avatarImg)
        binding.userName.text = signedInUser?.firstLastName
        binding.nacionalityText.text = signedInUser?.nationality
        if (signedInUser?.education == null) {
            binding.bookEmoji.visibility = View.INVISIBLE
        }
        else {
            binding.uni.text = signedInUser?.education
        }
        binding.headlineText.text = signedInUser?.headLine
        binding.countAnswers.text = signedInUser?.answers.toString()

        if (signedInUser?.connections?.isNullOrEmpty() == true) {
            binding.countConnections.text = "0"
        }
        else {
            binding.countConnections.text = signedInUser?.connections?.size.toString()
        }
    }

    fun readconnections() {

        auth.currentUser?.let {
            database.collection("users").document(it.uid)
                .collection("connections")
                .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = ArrayList<String>()
                    for (document in task.result!!) {
                        //val name = document.data["name"].toString()
                            val uid = document.id
                        list.add(uid)
                    }
                    signedInUser?.connections = list
                }
            }
        }
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



}