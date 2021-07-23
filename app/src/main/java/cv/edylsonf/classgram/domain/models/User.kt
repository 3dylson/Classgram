package cv.edylsonf.classgram.domain.models

data class User(
    var uid: String? = null,
    var username: String? = null,
    var firstLastName: String? = null,
    var email: String? = null,
    var profilePic: String? = null,
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    var isEmailVerified: Boolean? = null,
    var dateCreated: Long? = null,
    var addresses: List<Address>? = null,
    var followers: List<Follower>? = null
    )


