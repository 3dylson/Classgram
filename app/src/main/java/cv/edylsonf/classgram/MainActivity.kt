package cv.edylsonf.classgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import cv.edylsonf.classgram.databinding.ActivityMainBinding

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(){

    private var selectedTab = 0
    private val fragments: ArrayList<Fragment> by lazy {
        setup()
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val selectedTabId = savedInstanceState?.getInt(EXTRA_TAB_SELECTED) ?: R.id.home_button

        setupBottomBarActions(selectedTabId)


    }


    private fun setup() {
        TODO("Not yet implemented")
    }

    private fun setupBottomBarActions(selectedTabId: Int) {
        TODO("Not yet implemented")
    }


}
