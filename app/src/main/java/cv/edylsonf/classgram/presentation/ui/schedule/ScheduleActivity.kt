package cv.edylsonf.classgram.presentation.ui.schedule

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import cv.edylsonf.classgram.*
import cv.edylsonf.classgram.databinding.ActivityScheduleBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

class ScheduleActivity : BaseActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private var cardType: String = CALENDER_CARD
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.schedule_nav_host_fragment) as NavHostFragment).navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            cardType = it.getStringExtra(CARD_TYPE).toString()
        }

        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.schedule_nav)

        when (cardType) {
            CALENDER_CARD -> graph.setStartDestination(R.id.calendarFragment)
            AGENDA_CARD -> graph.setStartDestination(R.id.agendaFragment)
            TIMETABLE_CARD -> graph.setStartDestination(R.id.timetableFragment)
        }

        navController.graph = graph
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}