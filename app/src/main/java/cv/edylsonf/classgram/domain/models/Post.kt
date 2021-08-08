package cv.edylsonf.classgram.domain.models

data class Post(
    var text: String? = null,
    var imageUrl: String? = null,
    var creationTime: Long? = null,
    var comments: List<Comment>? = null,
    var mentions: List<User>? = null,
    var starsCount: Int? = null,
    var tags: List<Tag>? = null,
    var user: User? = null
)
