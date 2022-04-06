package cv.edylsonf.classgram.presentation.ui.schedule.event

import android.widget.TextView
import androidx.databinding.BindingAdapter
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.presentation.ui.utils.TimeUtils
import java.time.ZoneId
import java.time.ZonedDateTime

@BindingAdapter(
    "eventStart",
    "timeZoneId",
    "showTime",
    "eventLocation",
    requireAll = true
)
fun eventDateTimeLocation(
    textView: TextView,
    startTime: ZonedDateTime?,
    zoneId: ZoneId?,
    showTime: Boolean,
    location: String?
) {
    startTime ?: return
    zoneId ?: return
    val locationName = location ?: "-"
    val localStartTime = TimeUtils.zonedTime(startTime, zoneId)

    // For a11y, always use date, time, and location -> "May 7, 10:00 AM / Amphitheatre
    val dateTimeString = TimeUtils.dateTimeString(localStartTime)
    val contentDescription = textView.resources.getString(
        R.string.event_duration_location,
        dateTimeString,
        locationName
    )
    textView.contentDescription = contentDescription

    textView.text = if (showTime) {
        // Show date, time, and location, so just reuse the content description
        contentDescription
    } else {
        // Show location only
        locationName
    }
}