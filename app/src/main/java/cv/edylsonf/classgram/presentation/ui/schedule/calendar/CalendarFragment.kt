package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentCalendarBinding
import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment
import cv.edylsonf.classgram.presentation.ui.utils.daysOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment(), DayBinder<DayViewContainer> {

    private lateinit var binding: FragmentCalendarBinding
    private var toolbar: ActionBar? = null

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = (activity as AppCompatActivity).supportActionBar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO setup adapter and RV

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()

        binding.calendarOfFragCal.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            binding.calendarOfFragCal.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        binding.calendarOfFragCal.dayBinder = this
        binding.calendarOfFragCal.monthScrollListener = {
            toolbar?.title = if (it.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }
        binding.calendarOfFragCal.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = month.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].name.first().toString()
                            }
                    }
                }
            }


    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarOfFragCal.notifyDateChanged(it) }
            binding.calendarOfFragCal.notifyDateChanged(date)
            // TODO
            //updateAdapterForDate(date)
        }
    }


    override fun create(view: View) = DayViewContainer(view, object : DaySelection {
        override fun onDaySelected(dayDate: LocalDate) {
            selectDate(dayDate)
        }
    })

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.day = day
        val textView = container.binding.fragCalDayText
        val dotView = container.binding.fragCalDotView

        textView.text = day.date.dayOfMonth.toString()

        if (day.owner == DayOwner.THIS_MONTH) {
            textView.visibility = View.VISIBLE
            when (day.date) {
                today -> {
                    textView.setBackgroundResource(R.drawable.today_bg)
                    dotView.visibility = View.INVISIBLE
                }
                selectedDate -> {
                    textView.setBackgroundResource(R.drawable.selected_day_bg)
                    dotView.visibility = View.INVISIBLE
                }
                else -> {
                    textView.background = null
                    dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                }
            }
        } else {
            textView.visibility = View.INVISIBLE
            dotView.visibility = View.INVISIBLE
        }
    }
}