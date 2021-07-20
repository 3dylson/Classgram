package cv.edylsonf.classgram.domain.models

data class Post(
    var id: String = "",
    var text: String = "",
    var imageUrl: String = "",
    var creationTime: Long = 0,
    var comments: List<Comment>? = null,
    var mentions: List<User>? = null,
    var starsCount: Int = 0,
    var tags: List<Tag>? = null,
    var user: User? = null
)
