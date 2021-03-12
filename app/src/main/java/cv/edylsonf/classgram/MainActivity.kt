package cv.edylsonf.classgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.ui.home.HomeFragment
import cv.edylsonf.classgram.ui.profile.ProfileFragment

private lateinit var firebaseAnalytics: FirebaseAnalytics

class MainActivity : AppCompatActivity(){

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        setContentView(R.layout.activity_main)
        //TODO Call signout in profile view

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*val user = Firebase.auth.currentUser
        if (user!= null) {
            loadFragment(homeFragment)
        } else {
            findNavController(R.id.mainActivity).navigate(R.id.loginActivity2)
        }*/

        loadFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.home_button -> {
                    loadFragment(homeFragment)
                    true
                }
                R.id.profile_button -> {
                    loadFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navigationContainer, fragment)
            .commit()
    }

}