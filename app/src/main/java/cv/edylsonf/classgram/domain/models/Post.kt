package cv.edylsonf.classgram.domain.models

import androidx.annotation.Keep

@Keep
data class Post(
    var text: String? = null,
    var imageUrl: String? = null,
    var creationTime: Long? = null,
    var comments: List<String>? = null,
    var mentions: List<String>? = null,
    var upCount: Int? = 0,
    var viewsCount: Int? = 0,
    var ups: MutableMap<String, Boolean>? = HashMap(),
    var tags: List<String>? = null,
    var user: UserPostDetail? = null
)
