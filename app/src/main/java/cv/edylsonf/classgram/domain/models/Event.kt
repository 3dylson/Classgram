package cv.edylsonf.classgram.domain.models

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class Event(
    val id: String,
    val name: String,
    val color: Color,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val description: String? = null,
)
