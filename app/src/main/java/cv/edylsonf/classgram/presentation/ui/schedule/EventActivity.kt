package cv.edylsonf.classgram.presentation.ui.schedule

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import cv.edylsonf.classgram.*
import cv.edylsonf.classgram.databinding.ActivityEventBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

class EventActivity : BaseActivity() {

    private lateinit var binding: ActivityEventBinding
    private lateinit var cardType: String
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.schedule_nav_host_fragment) as NavHostFragment).navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            if (it.getStringExtra(CARD_TYPE).equals(CALENDER_CARD)) cardType = CALENDER_CARD
            if (it.getStringExtra(CARD_TYPE).equals(AGENDA_CARD)) cardType = AGENDA_CARD
            if (it.getStringExtra(CARD_TYPE).equals(TIMETABLE_CARD)) cardType = TIMETABLE_CARD
        }

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.schedule_nav)

        when(cardType) {
            CALENDER_CARD -> graph.setStartDestination(R.id.calendarFragment)
        }

        navController.graph = graph

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}