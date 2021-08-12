package cv.edylsonf.classgram.domain.models

data class Post(
    var text: String? = null,
    var imageUrl: String? = null,
    var creationTime: Long? = null,
    var comments: List<String>? = null,
    var mentions: List<String>? = null,
    var upCount: Int? = null,
    var ups: MutableMap<String, Boolean> = HashMap(),
    var tags: List<String>? = null,
    var user: User? = null
)
