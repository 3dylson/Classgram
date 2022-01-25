package cv.edylsonf.classgram.presentation.ui.schedule.calendar

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentCalendarBinding
import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.domain.models.EventId
import cv.edylsonf.classgram.presentation.ui.schedule.event.EventTimeHeadersDecoration
import cv.edylsonf.classgram.presentation.ui.schedule.event.EventsAdapter
import cv.edylsonf.classgram.presentation.ui.schedule.event.OnEventClickListener
import cv.edylsonf.classgram.presentation.ui.schedule.timetable.sampleEvents
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment
import cv.edylsonf.classgram.presentation.ui.utils.TimeUtils
import cv.edylsonf.classgram.presentation.ui.utils.clearDecorations
import cv.edylsonf.classgram.presentation.ui.utils.daysOfWeekFromLocale
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment(), DayBinder<DayViewContainer>, OnEventClickListener {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var fab: FloatingActionButton

    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val currentMonth = YearMonth.now()

    private val events = mutableMapOf<LocalDate, List<Event>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        toolbar?.setDisplayHomeAsUpEnabled(true)
        fab = binding.fragCalFAB

        fab.setOnClickListener { newEventDialog() }

        eventRecyclerView = binding.fragCalRv

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO move to view model
        val timeZoneId = flow<ZoneId> {
            emit(ZoneId.systemDefault())
        }.stateIn(lifecycleScope, Lazily, ZoneId.systemDefault())

        val daysOfWeek = daysOfWeekFromLocale()

        eventsAdapter = EventsAdapter(
            timeZoneId,
            viewLifecycleOwner,
            this
        )
        eventRecyclerView.apply {
            adapter = eventsAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        val listOfSampleEvent = sampleEvents
        eventsAdapter.submitList(listOfSampleEvent)
        eventRecyclerView.run {
            // Recreate the decoration used for the sticky time headers
            clearDecorations()
            if (listOfSampleEvent.isNotEmpty()) {
                addItemDecoration(
                    EventTimeHeadersDecoration(
                        context, listOfSampleEvent.map { it }, ZoneId.systemDefault()
                    )
                )
            }
        }

        setupCalendar(daysOfWeek, savedInstanceState)


    }

    private fun setupCalendar(
        daysOfWeek: Array<DayOfWeek>,
        savedInstanceState: Bundle?
    ) {
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
        binding.calendarOfFragCal.monthScrollListener = { calendar ->
            toolbar?.title = if (calendar.year == today.year) {
                TimeUtils.fullMonthString(calendar.yearMonth).replaceFirstChar { it.uppercase() }
            } else {
                TimeUtils.monthYearString(calendar.yearMonth).replaceFirstChar { it.uppercase() }
            }

            if (calendar.month == today.month.value) selectDate(today)
            // Select the first day of the month when
            // we scroll to a new month.
            else selectDate(calendar.yearMonth.atDay(1))
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calendar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.jumpToToday -> {
                binding.calendarOfFragCal.scrollToMonth(currentMonth)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun newEventDialog() {
        findNavController().navigate(R.id.action_calendarFragment_to_newEventDialogFragment)

    }

    override fun onResume() {
        super.onResume()
        toolbar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarOfFragCal.notifyDateChanged(it) }
            binding.calendarOfFragCal.notifyDateChanged(date)
            updateAdapterForDate(date)

        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        //TODO update adapter value

        binding.fragCalSelectedDateText.text = TimeUtils.dateYearString(date)
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

    override fun openEventDetail(id: EventId) {
        val directions = CalendarFragmentDirections.actionCalendarFragmentToEditEventDialogFragment(id)
        findNavController().navigate(directions)
    }
}