package cv.edylsonf.classgram.presentation.ui.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

object TimeUtils {

    fun zonedTime(time: ZonedDateTime, zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
        return ZonedDateTime.ofInstant(time.toInstant(), zoneId)
    }

    /** Format a time to show weekday, month and day, e.g. "Tue, May 7" */
    fun weekDateString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("EEE, MMM d").format(startTime)
    }

    /** Format a time to show month and day, e.g. "May 7" */
    fun dateString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("MMM d").format(startTime)
    }

    /** Format a time to show month, day, and time, e.g. "May 7, 10:00 AM" */
    fun dateTimeString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("MMM d, h:mm a").format(startTime)
    }

    /** Format a time to show day, month and year, e.g. "7 Feb 1999" */
    fun dateYearString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("d MMM yyyy").format(startTime)
    }

    /** Format a time to show month and year, e.g. "Feb 1999" */
    fun monthYearString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("MMM yyyy").format(startTime)
    }

    /** Format a time to show complete month, e.g. "February" */
    fun fullMonthString(startTime: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern("MMMM").format(startTime)
    }

}