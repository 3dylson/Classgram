package cv.edylsonf.classgram.domain.models

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class Event(
    val id: String,
    val name: String,
    val color: Color? = null,
    val start: ZonedDateTime,
    val end: ZonedDateTime,
    val description: String? = null,
)
