package cv.edylsonf.classgram.presentation.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cv.edylsonf.classgram.AGENDA_CARD
import cv.edylsonf.classgram.CALENDER_CARD
import cv.edylsonf.classgram.CARD_TYPE
import cv.edylsonf.classgram.TIMETABLE_CARD
import cv.edylsonf.classgram.databinding.FragmentScheduleBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment


class ScheduleFragment : BaseFragment() {

    private lateinit var binding: FragmentScheduleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater)

        with(binding) {
            calendarCard.setOnClickListener { navToCalendar() }
            agendaCard.setOnClickListener { navToAgenda() }
            timetableCard.setOnClickListener { navToTimetable() }
        }

        return binding.root
    }


    private fun navToTimetable() {
        navToSpecificCard(TIMETABLE_CARD)
    }

    private fun navToAgenda() {
        navToSpecificCard(AGENDA_CARD)
    }

    private fun navToCalendar() {
        navToSpecificCard(CALENDER_CARD)
    }

    private fun navToSpecificCard(cardType: String) {
        val intent = Intent(activity, ScheduleActivity::class.java)
        intent.putExtra(CARD_TYPE, cardType)
        startActivity(intent)
    }
}