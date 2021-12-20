package cv.edylsonf.classgram.domain.models

data class Chat(
    val lastMessageTimestamp: Long? = null,
    val participants: List<String>? = null,
    val messages: List<Message>? = null,

)
