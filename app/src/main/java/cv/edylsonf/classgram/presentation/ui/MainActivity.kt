package cv.edylsonf.classgram.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityMainBinding
import cv.edylsonf.classgram.domain.models.UserPostDetail
import cv.edylsonf.classgram.presentation.ui.home.CreatePostActivity
import cv.edylsonf.classgram.presentation.ui.login.LoginActivity
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity
import cv.edylsonf.classgram.presentation.viewModels.SharedSignedUserViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var signedInUser: UserPostDetail? = null
    private lateinit var fab: FloatingActionButton
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
    }
    private val bottomNavView by lazy { binding.bottomNavigationView }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val model: SharedSignedUserViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fab = binding.fab

        /*findViewById<RecyclerView>(R.id.rvPosts).setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) fab.hide()
            else fab.show()
        }*/

        database = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        database.firestoreSettings = settings

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Setup the ActionBar with navController and 4 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_nav,
                R.id.search_nav,
                R.id.schedule_nav,
                R.id.profile_nav
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Handle Navigation item clicks
        // This works with no further action if the menu and destination id’s match.
        bottomNavView.setupWithNavController(navController)

        //Click listeners
        with(binding) {
            fab.setOnClickListener { createPost() }
        }

    }


    private fun createPost() {
        val intent = Intent(this, CreatePostActivity::class.java)
        //TODO Use Shared View Model
        intent.putExtra("signedInUser", signedInUser)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d(TAG, "AuthStateListener triggered. User: ${it.currentUser}")
            if (it.currentUser == null) {
                startLogin()
            } else {
                val usersDoc = database.collection("users")
                    .document(uid)
                //.get()
                usersDoc.addSnapshotListener { userSnapshot, exception ->
                    if (exception != null || userSnapshot == null) {
                        Log.w(
                            TAG,
                            "Unable to retrieve user. Error=$exception, snapshot=$userSnapshot"
                        )
                        return@addSnapshotListener
                    }
                    signedInUser = userSnapshot.toObject(UserPostDetail::class.java)
                    //intent.putExtra("signedInUser",signedInUser)
                    signedInUser?.let { user -> model.selectUser(user) }
                    Log.i(TAG, "signed in user: $signedInUser")
                }
            }
        }
    }


    private fun startLogin() {
        val logoutIntent = Intent(this, LoginActivity::class.java)
        logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(logoutIntent)
        finish()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
