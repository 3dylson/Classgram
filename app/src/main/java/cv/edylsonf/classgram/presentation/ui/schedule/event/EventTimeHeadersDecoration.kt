package cv.edylsonf.classgram.presentation.ui.schedule.event

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.graphics.withTranslation
import androidx.core.text.inSpans
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.presentation.ui.utils.isRtl
import cv.edylsonf.classgram.presentation.ui.utils.newStaticLayout
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * A [RecyclerView.ItemDecoration] which draws sticky headers for a given list of events.
 */
class EventTimeHeadersDecoration(
    context: Context,
    events: List<Event>,
    zoneId: ZoneId
) : RecyclerView.ItemDecoration() {

    private val paint: TextPaint
    private val width: Int
    private val padding: Int
    private val timeTextSize: Int
    private val meridiemTextSize: Int
    private val timeFormatter = DateTimeFormatter.ofPattern("h:mm")
    private val meridiemFormatter = DateTimeFormatter.ofPattern("a")

    private val timeTextSizeSpan: AbsoluteSizeSpan
    private val meridiemTextSizeSpan: AbsoluteSizeSpan
    private val boldSpan = StyleSpan(Typeface.BOLD)

    init {
        val attrs = context.obtainStyledAttributes(
            R.style.Widget_ClassgramSched_TimeHeaders,
            R.styleable.TimeHeader
        )
        paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = attrs.getColorOrThrow(R.styleable.TimeHeader_android_textColor)
            try {
                typeface = ResourcesCompat.getFont(
                    context,
                    attrs.getResourceIdOrThrow(R.styleable.TimeHeader_android_fontFamily)
                )
            } catch (_: Exception) {
                // ignore
            }
        }
        width = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_android_width)
        padding = attrs.getDimensionPixelSize(R.styleable.TimeHeader_android_padding, 0)
        timeTextSize = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_timeTextSize)
        meridiemTextSize =
            attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_meridiemTextSize)
        attrs.recycle()

        timeTextSizeSpan = AbsoluteSizeSpan(timeTextSize)
        meridiemTextSizeSpan = AbsoluteSizeSpan(meridiemTextSize)
    }

    // Get the events index:start time and create header layouts for each
    private val timeSlots: Map<Int, StaticLayout> =
        indexEventHeaders(events, zoneId).map {
            it.first to createHeader(it.second)
        }.toMap()


    /**
     * Loop over each child and draw any corresponding headers i.e. items who's position is a key in
     * [timeSlots]. We also look back to see if there are any headers _before_ the first header we
     * found i.e. which needs to be sticky.
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (timeSlots.isEmpty() || parent.isEmpty()) return

        val isRtl = parent.isRtl()
        if (isRtl) {
            c.save()
            c.translate((parent.width - width).toFloat(), 0f)
        }

        val parentPadding = parent.paddingTop

        var earliestPosition = Int.MAX_VALUE
        var previousHeaderPosition = -1
        var previousHasHeader = false
        var earliestChild: View? = null
        for (i in parent.childCount - 1 downTo 0) {
            val child = parent.getChildAt(i)
            if (child == null) {
                // This should not be null, but observed null at times.
                // Guard against it to avoid crash and log the state.
                Timber.w(
                    """View is null. Index: $i, childCount: ${parent.childCount},
                        |RecyclerView.State: $state""".trimMargin()
                )
                continue
            }

            if (child.y > parent.height || (child.y + child.height) < 0) {
                // Can't see this child
                continue
            }

            val position = parent.getChildAdapterPosition(child)
            if (position < 0) {
                continue
            }
            if (position < earliestPosition) {
                earliestPosition = position
                earliestChild = child
            }

            val header = timeSlots[position]
            if (header != null) {
                drawHeader(c, child, parentPadding, header, child.alpha, previousHasHeader)
                previousHeaderPosition = position
                previousHasHeader = true
            } else {
                previousHasHeader = false
            }
        }

        if (earliestChild != null && earliestPosition != previousHeaderPosition) {
            // This child needs a sicky header
            findHeaderBeforePosition(earliestPosition)?.let { stickyHeader ->
                previousHasHeader = previousHeaderPosition - earliestPosition == 1
                drawHeader(c, earliestChild, parentPadding, stickyHeader, 1f, previousHasHeader)
            }
        }

        if (isRtl) {
            c.restore()
        }
    }

    private fun findHeaderBeforePosition(position: Int): StaticLayout? {
        for (headerPos in timeSlots.keys.reversed()) {
            if (headerPos < position) {
                return timeSlots[headerPos]
            }
        }
        return null
    }

    private fun drawHeader(
        canvas: Canvas,
        child: View,
        parentPadding: Int,
        header: StaticLayout,
        headerAlpha: Float,
        previousHasHeader: Boolean
    ) {
        val childTop = child.y.toInt()
        val childBottom = childTop + child.height
        var top = (childTop + padding).coerceAtLeast(parentPadding)
        if (previousHasHeader) {
            top = top.coerceAtMost(childBottom - header.height - padding)
        }
        paint.alpha = (headerAlpha * 255).toInt()
        canvas.withTranslation(y = top.toFloat()) {
            header.draw(canvas)
        }
    }


    /**
     * Create a header layout for the given [startTime].
     */
    private fun createHeader(startTime: ZonedDateTime): StaticLayout {
        val text = SpannableStringBuilder().apply {
            inSpans(timeTextSizeSpan) {
                append(timeFormatter.format(startTime))
            }
            append(System.lineSeparator())
            inSpans(meridiemTextSizeSpan, boldSpan) {
                append(meridiemFormatter.format(startTime).uppercase(Locale.getDefault()))
            }
        }
        return newStaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false)
    }
}