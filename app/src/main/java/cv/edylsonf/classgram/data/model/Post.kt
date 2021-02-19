package cv.edylsonf.classgram.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
data class Post(
        @PrimaryKey
       var uid: String? = "",
       var userId: String? = "",
       var text: String? = "",
       var starCount: Int = 0,
       var stars: MutableMap<String, Boolean> = HashMap()

) {

    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
                "uid" to uid,
                "userId" to userId,
                "text" to text,
                "starCount" to starCount,
                "stars" to stars
        )
    }
}