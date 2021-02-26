package cv.edylsonf.classgram.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
data class User(
    @PrimaryKey
    var userId: String = "",
    var name: String? = null,
    var email: String? = null
)