package cv.edylsonf.classgram.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.REQUEST_IMAGE_CAPTURE
import cv.edylsonf.classgram.databinding.FragmentHomeBinding
import cv.edylsonf.classgram.domain.models.Post

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private lateinit var posts: MutableList<Post>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)
        auth = Firebase.auth
        database = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        database.firestoreSettings = settings

        posts = mutableListOf()
        //loadPosts()


        return binding.root
    }

    private fun loadPosts() {
        val postsDoc = database.collection("posts")
            .limit(20)
            .orderBy("creationTime", Query.Direction.DESCENDING)
        postsDoc.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.w(TAG, "Unable to retrieve data. Error=$exception, snapshot=$snapshot")
                return@addSnapshotListener
            }

            Log.d(TAG, "Posts retrieved:${snapshot.documents.size}")

            val posts = snapshot.toObjects(Post::class.java)

            for (post in posts) {

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_action_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cam -> openCamera()
        }
        return true
    }

    //TODO send photo to ImageView
    private fun openCamera() {
        startActivityForResult(Intent(context, CameraActivity::class.java), REQUEST_IMAGE_CAPTURE)
    }



}