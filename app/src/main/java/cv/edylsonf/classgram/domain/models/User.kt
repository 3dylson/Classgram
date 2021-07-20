package cv.edylsonf.classgram.domain.models

data class User(
    var uid: String = "",
    var username: String = "",
    var email: String = "",
    var dateCreated: Long = 0,
    var addresses: List<Address>? = null,
    var followers: List<Follower>? = null
    )


