package cv.edylsonf.classgram.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid: String? = null,
    var username: String? = null,
    var headLine: String? = null,
    var firstLastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var nationality: String? = null,
    var education: String? = null,
    var profilePic: String? = null,
    var answers: Int? = null,
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    var isEmailVerified: Boolean? = null,
    var dateCreated: Long? = null,
    var addresses: List<String>? = null,
    var connections: List<String>? = null,
    var posts: List<String>? = null
    ) : Parcelable


