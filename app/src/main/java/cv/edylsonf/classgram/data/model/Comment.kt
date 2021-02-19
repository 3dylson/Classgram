package cv.edylsonf.classgram.data.model

import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
data class Comment(
        var uid: String? = "",
        var parent: Boolean? = false,
        var text: String? = "",
        var userId: String? = ""
)
