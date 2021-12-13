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
import cv.edylsonf.classgram.EXTRA_TAB_TITLE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.REQUEST_IMAGE_CAPTURE
import cv.edylsonf.classgram.databinding.FragmentHomeBinding
import cv.edylsonf.classgram.domain.models.Chat
import cv.edylsonf.classgram.domain.models.Post
import cv.edylsonf.classgram.presentation.ui.chat.ChatActivity
import cv.edylsonf.classgram.presentation.ui.login.SignupActivity
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment

private const val TAG = "HomeFragment"

open class HomeFragment : BaseFragment(),
            PostAdapter.OnPostSelectedListener {

    private var fragTitle = "Classgram"
    private var toolbar: ActionBar? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var query: Query

    private lateinit var adapter: PostAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomAppBar: BottomAppBar

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

        toolbar?.title = fragTitle

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

        query = database.collection("posts")
            .limit(20)
            .orderBy("creationTime", Query.Direction.DESCENDING)

        setup()
        profilePosts()

        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    0 -> {
                        fab.show()
                        bottomAppBar.performShow()
                    }
                    else -> {
                        fab.hide()
                        bottomAppBar.performHide()
                    }

                }
            }

        })*/


        return binding.root
    }

    private fun setup(){
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
                Snackbar.make(binding.root,
                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = adapter

        //posts = mutableListOf()
        /*val context1 = container?.context
        adapter = context1?.let { PostAdapter(it,posts) }!!*/
        /*adapter = PostAdapter(requireContext(),posts)
        binding.rvPosts.adapter = adapter
        recyclerView.setHasFixedSize(true)
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())*/
    }

    private fun profilePosts() {
        /*var postsDoc = database.collection("posts")
            .limit(20)
            .orderBy("creationTime", Query.Direction.DESCENDING)*/

        val homeOrProfile = activity?.intent?.getStringExtra("profile")
        if (homeOrProfile != null ) {
            query = query.whereEqualTo("user.uid",uid)
            adapter.setQuery(query)
        }
        /*postsDoc.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.w(TAG, "Unable to retrieve data. Error=$exception, snapshot=$snapshot")
                return@addSnapshotListener
            }

            Log.d(TAG, "Posts retrieved:${snapshot.documents.size}")

            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post $post")
            }

        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_action_menu,menu)
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
        Snackbar.make(binding.root,
            "Post selected "+post.id, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        toolbar?.title = fragTitle
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