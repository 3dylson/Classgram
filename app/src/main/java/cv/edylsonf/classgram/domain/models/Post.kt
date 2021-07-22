package cv.edylsonf.classgram.domain.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    var text: String? = null,
    var imageUrl: String? = null,
    @ServerTimestamp var creationTime: Date? = null,
    var comments: List<Comment>? = null,
    var mentions: List<User>? = null,
    var starsCount: Int? = null,
    var tags: List<Tag>? = null,
    var user: User? = null
)
