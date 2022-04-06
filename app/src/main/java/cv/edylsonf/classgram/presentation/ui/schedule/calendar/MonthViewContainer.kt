package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import cv.edylsonf.classgram.databinding.FragCalCalendarHeaderBinding

class MonthViewContainer(view: View) : ViewContainer(view) {
  val legendLayout = FragCalCalendarHeaderBinding.bind(view).legendLayout.root
}