package cv.edylsonf.classgram.presentation.ui.schedule

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.fragment.NavHostFragment
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

        when(cardType) {
            CALENDER_CARD -> navController.navigate(R.id.calendarFragment)
        }

    }



}