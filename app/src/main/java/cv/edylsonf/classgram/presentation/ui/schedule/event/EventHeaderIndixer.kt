package cv.edylsonf.classgram.presentation.ui.schedule.event

import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.presentation.ui.utils.TimeUtils
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

/**
 * Find the first event at each start time (rounded down to nearest minute) and return pairs of
 * index to start time. Assumes that [events] are sorted by ascending start time.
 */

fun indexEventHeaders(events: List<Event>, zoneId: ZoneId): List<Pair<Int, ZonedDateTime>> {
    return events
        .mapIndexed { index, event ->
            index to TimeUtils.zonedTime(event.start, zoneId)
        }
        .distinctBy { it.second.truncatedTo(ChronoUnit.MINUTES) }
}