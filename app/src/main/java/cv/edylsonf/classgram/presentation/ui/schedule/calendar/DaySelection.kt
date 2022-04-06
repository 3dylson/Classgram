package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import java.time.LocalDate

interface DaySelection {

    fun onDaySelected(dayDate: LocalDate)

}