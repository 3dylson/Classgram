package cv.edylsonf.classgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import cv.edylsonf.classgram.presentation.views.TweetFragment

class MainActivity : AppCompatActivity(){

    private val tweetFragment by lazy {TweetFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*bottomNavigationView.background = null*/
        /*bottomNavigationView.menu.getItem(2).isEnabled = false*/

        loadFragment(TweetFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home_button -> {
                    loadFragment(tweetFragment)
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