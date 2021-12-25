package cv.edylsonf.classgram.presentation.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import cv.edylsonf.classgram.EXTRA_TAB_TITLE
import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt


class ScheduleFragment : BaseFragment() {

    private var fragTitle: String? = null
    private var toolbar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragTitle = it.getString(EXTRA_TAB_TITLE)
        }

        toolbar = (activity as AppCompatActivity).supportActionBar

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this).start()

        setContent {
            CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
                ClassgramTheme {
                    Schedule(events = sampleEvents)
                }
            }
        }

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    @Composable
    fun Schedule(
        events: List<Event>,
        modifier: Modifier = Modifier,
        eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
        dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
        minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
        maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate(),
    ) {
        // TODO compute dayWidth base on the device width.
        val dayWidth = 256.dp
        val hourHeight = 64.dp
        val verticalScrollState = rememberScrollState()
        val horizontalScrollState = rememberScrollState()
        var sidebarWidth by remember { mutableStateOf(0) }
        Column(modifier = modifier) {
            ScheduleHeader(
                minDate = minDate,
                maxDate = maxDate,
                dayWidth = dayWidth,
                dayHeader = dayHeader,
                modifier = Modifier
                    .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
                    .horizontalScroll(horizontalScrollState)
            )
            Row(modifier = Modifier.weight(1f)) {
                ScheduleSidebar(
                    hourHeight = hourHeight,
                    modifier = Modifier
                        .verticalScroll(verticalScrollState)
                        .onGloballyPositioned { sidebarWidth = it.size.width }
                )
                BasicSchedule(
                    events = events,
                    eventContent = eventContent,
                    minDate = minDate,
                    maxDate = maxDate,
                    dayWidth = dayWidth,
                    hourHeight = hourHeight,
                    modifier = Modifier
                        .weight(1f) // Fill remaining space in the column
                        .verticalScroll(verticalScrollState)
                        .horizontalScroll(horizontalScrollState)
                )
            }
        }
    }


    @Composable
    private fun BasicSchedule(
        events: List<Event>,
        modifier: Modifier = Modifier,
        eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
        minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
        maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate(),
        dayWidth: Dp,
        hourHeight: Dp,
    ) {
        val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
        val dividerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
        Layout(
            modifier = modifier
                .drawBehind {
                    repeat(23) {
                        drawLine(
                            dividerColor,
                            start = Offset(0f, (it + 1) * hourHeight.toPx()),
                            end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    repeat(numDays - 1) {
                        drawLine(
                            dividerColor,
                            start = Offset((it + 1) * dayWidth.toPx(), 0f),
                            end = Offset((it + 1) * dayWidth.toPx(), size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                },
            content = {
                events.sortedBy(Event::start).forEach { event ->
                    Box(modifier = Modifier.eventData(event)) {
                        eventContent(event)
                    }
                }
            }
        ) { measurables, constraints ->
            val height = hourHeight.roundToPx() * 24
            val width = dayWidth.roundToPx() * numDays
            val placeablesWithEvents = measurables.map { measurable ->
                val event = measurable.parentData as Event
                val eventDurationMinutes = ChronoUnit.MINUTES.between(event.start, event.end)
                val eventHeight = ((eventDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val placeable = measurable.measure(constraints.copy(minWidth = dayWidth.roundToPx(), maxWidth = dayWidth.roundToPx(), minHeight = eventHeight, maxHeight = eventHeight))
                Pair(placeable, event)
            }
            layout(width, height) {
                placeablesWithEvents.forEach { (placeable, event) ->
                    val eventOffsetMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, event.start.toLocalTime())
                    val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                    val eventOffsetDays = ChronoUnit.DAYS.between(minDate, event.start.toLocalDate()).toInt()
                    val eventX = eventOffsetDays * dayWidth.roundToPx()
                    placeable.place(eventX, eventY)
                }
            }
        }
    }

    @Composable
    fun ScheduleHeader(
        minDate: LocalDate,
        maxDate: LocalDate,
        dayWidth: Dp,
        modifier: Modifier = Modifier,
        dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
    ) {
        Row(modifier = modifier) {
            val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
            repeat(numDays) { i ->
                Box(modifier = Modifier.width(dayWidth)) {
                    dayHeader(minDate.plusDays(i.toLong()))
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ScheduleHeaderPreview() {
        ClassgramTheme {
            ScheduleHeader(
                minDate = LocalDate.now(),
                maxDate = LocalDate.now().plusDays(5),
                dayWidth = 256.dp,
            )
        }
    }


    private class EventDataModifier(
        val event: Event,
    ) : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?) = event
    }

    private fun Modifier.eventData(event: Event) = this.then(EventDataModifier(event))


    @Preview(showBackground = true)
    @Composable
    fun SchedulePreview() {
        ClassgramTheme {
            Schedule(sampleEvents)
        }
    }



}