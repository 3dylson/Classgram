package cv.edylsonf.classgram.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Message
    (
    val id: String,
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: String,
    var outgoing: Boolean = false
)
