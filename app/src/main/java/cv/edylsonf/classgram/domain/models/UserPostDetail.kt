package cv.edylsonf.classgram.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserPostDetail(
    var uid: String? = null,
    var username: String? = null,
    var headLine: String? = null,
    var firstLastName: String? = null,
    var nationality: String? = null,
    var education: String? = null,
    var answers: Int? = 0,
    var profilePic: String? = null
) : Parcelable
