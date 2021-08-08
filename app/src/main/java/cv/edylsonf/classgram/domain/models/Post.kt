package cv.edylsonf.classgram.domain.models

data class Post(
    var text: String? = null,
    var imageUrl: String? = null,
    var creationTime: Long? = null,
    var comments: List<String>? = null,
    var mentions: List<String>? = null,
    var starsCount: Int? = null,
    var tags: List<String>? = null,
    var user: User? = null
)
