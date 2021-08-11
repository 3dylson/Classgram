package cv.edylsonf.classgram.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import cv.edylsonf.classgram.EXTRA_TAB_SELECTED
import cv.edylsonf.classgram.EXTRA_TAB_TITLE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityMainBinding
import cv.edylsonf.classgram.domain.models.User
import cv.edylsonf.classgram.presentation.ui.home.HomeFragment
import cv.edylsonf.classgram.presentation.ui.login.LoginActivity
import cv.edylsonf.classgram.presentation.ui.profile.ProfileFragment

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(){

    private var signedInUser: User? = null
    private var selectedTab = 0
    private val fragments: ArrayList<Fragment> by lazy {
        setup()
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val selectedTabId = savedInstanceState?.getInt(EXTRA_TAB_SELECTED) ?: R.id.home_button

        setupBottomBarActions(selectedTabId)

        /*when (selectedTabId){
            0 -> supportActionBar?.title = "Classgram"
            1 -> supportActionBar?.title = "username"
            else -> supportActionBar?.title = "Classgram"
        }*/


    }

    override fun onStart() {
        super.onStart()

        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d(TAG, "AuthStateListener triggered. User: ${it.currentUser}")
            if (it.currentUser == null){
                startLogin()
            }
            else {
                database.collection("users")
                    .document(it.currentUser?.uid as String)
                    .get()
                    .addOnSuccessListener { userSnapshot ->
                        signedInUser = userSnapshot.toObject(User::class.java)
                        Log.i(TAG, "signed in user: $signedInUser")
                    }
                    .addOnFailureListener { exception ->
                        Log.i(TAG, "Failure fetching signed in user", exception)
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


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(EXTRA_TAB_SELECTED, binding.bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setup(): ArrayList<Fragment> {

        val argHome = Bundle()
        argHome.putString(EXTRA_TAB_TITLE, getString(R.string.home))

        val home = HomeFragment()
        home.arguments = argHome


        /*val argSearch = Bundle()
        argSearch.putString(EXTRA_TAB_TITLE, getString(R.string.discover))

        val search = SearchFragment()
        search.arguments = argSearch*/


        /*val argSchedule = Bundle()
        argSchedule.putString(EXTRA_TAB_TITLE, getString(R.string.schedule))

        val schedule = ScheduleFragment()
        schedule.arguments = argSchedule*/


        val argProfile = Bundle()
        argProfile.putString(EXTRA_TAB_TITLE, getString(R.string.me))

        val profile = ProfileFragment()
        profile.arguments = argProfile


        return arrayListOf(home,profile)
    }

    private fun setupBottomBarActions(selectedTabId: Int) {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val index: Int = when (item.itemId) {
                R.id.home_button    -> 0
                R.id.profile_button -> 1
                else                -> 0
            }
            //Change Action bar title
            when (index) {
                0     -> supportActionBar?.title = "Classgram"
                1     -> supportActionBar?.title = "username"
                else  -> supportActionBar?.title = "Classgram"
            }

            switchFragments(index)
            selectedTab = index

            return@setOnNavigationItemSelectedListener true
        }

        binding.bottomNavigationView.selectedItemId = selectedTabId
    }

    private fun switchFragments(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = "${fragments[index].arguments?.get(EXTRA_TAB_TITLE)}"

        // if the fragment has not yet been added to the container, add it first
        if(supportFragmentManager.findFragmentByTag(tag) == null) {
            transaction.add(R.id.fragmentContainerView, fragments[index], tag)

        } else {
            if (fragments[index] === supportFragmentManager.findFragmentByTag(tag)) {
                fragments[index].onResume()

            } else {
                transaction.replace(R.id.fragmentContainerView, fragments[index], tag)
            }
        }

        transaction.hide(fragments[selectedTab])
        transaction.show(fragments[index])
        transaction.commit()

    }


}
