package cv.edylsonf.classgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import cv.edylsonf.classgram.ui.home.HomeFragment
import cv.edylsonf.classgram.ui.profile.ProfileFragment

private const val TAG = "MainActivity"

private lateinit var firebaseAnalytics: FirebaseAnalytics

class MainActivity : AppCompatActivity(){

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        setContentView(R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        loadFragment(homeFragment)

        val navController = findNavController(R.id.fragmentContainerView)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        bottomNavigationView.setupWithNavController(navController)
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
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    /*override fun onBackPressed() {

        if()
        super.onBackPressed()
    }*/
}