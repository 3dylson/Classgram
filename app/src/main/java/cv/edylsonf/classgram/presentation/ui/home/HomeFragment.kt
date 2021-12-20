package cv.edylsonf.classgram.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.REQUEST_IMAGE_CAPTURE
import cv.edylsonf.classgram.databinding.FragmentHomeBinding
import cv.edylsonf.classgram.presentation.ui.chat.ChatActivity
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment

private const val TAG = "HomeFragment"

open class HomeFragment : BaseFragment(),
    PostAdapter.OnPostSelectedListener {

    private var toolbar: ActionBar? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    lateinit var query: Query

    lateinit var adapter: PostAdapter
    lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = (activity as AppCompatActivity).supportActionBar

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        recyclerView = binding.rvPosts

        fab = requireActivity().findViewById(R.id.fab)
        bottomAppBar = requireActivity().findViewById(R.id.bottomAppBar)


        setHasOptionsMenu(true)

        auth = Firebase.auth
        database = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        database.firestoreSettings = settings

        setQuery()
        setup()


        return binding.root
    }

    open fun setQuery() {
        query = database.collection("posts")
            .limit(20)
            .orderBy("creationTime", Query.Direction.DESCENDING)
    }


    private fun setup() {
        adapter = object : PostAdapter(query, this@HomeFragment) {
            override fun onDataChanged() {
                // Show/hide content if the query returns empty.
                // TODO empty view
                if (itemCount == 0) {
                    binding.rvPosts.visibility = View.GONE
                    //binding.viewEmpty.visibility = View.VISIBLE
                } else {
                    binding.rvPosts.visibility = View.VISIBLE
                    //binding.viewEmpty.visibility = View.GONE
                }
            }

            // TODO change on production
            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(
                    binding.root,
                    "Error: check logs for info.", Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = adapter

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cam -> openCamera()
            R.id.notifications -> navChatScreen()
        }
        return true
    }

    private fun navChatScreen() {
        val intent = Intent(activity, ChatActivity::class.java)
        activity?.startActivity(intent)
    }

    //TODO send photo to ImageView
    private fun openCamera() {
        startActivityForResult(Intent(context, CameraActivity::class.java), REQUEST_IMAGE_CAPTURE)
    }

    override fun onPostSelected(post: DocumentSnapshot) {
        Snackbar.make(
            binding.root,
            "Post selected " + post.id, Snackbar.LENGTH_LONG
        ).show()
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}