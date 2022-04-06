package cv.edylsonf.classgram.domain.models

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.time.ZonedDateTime

typealias EventId = String

data class Event(
    val id: EventId,
    val name: String,
    val location: String? = null,
    val color: Color? = null,
    val start: ZonedDateTime,
    val end: ZonedDateTime,
    val description: String? = null,
) {
    fun isOverlapping(event: Event): Boolean {
        return this.start < event.end && this.end > event.start
    }

    fun hasLocation(): Boolean {
        return location != null
    }
}
