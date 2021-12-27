package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import cv.edylsonf.classgram.databinding.FragCalCalendarDayBinding

class DayViewContainer(view: View, listener: DaySelection) : ViewContainer(view)  {

    lateinit var day: CalendarDay // Will be set when this container is bound.
    val binding = FragCalCalendarDayBinding.bind(view)


    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                listener.onDaySelected(day.date)
            }
        }
    }
}