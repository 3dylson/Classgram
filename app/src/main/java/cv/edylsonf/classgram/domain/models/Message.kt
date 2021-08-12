package cv.edylsonf.classgram.domain.models

data class Message(val id: String,
                   val user: String,
                   val content: String,
                   val timestamp: String,
                   var outgoing: Boolean = false)
