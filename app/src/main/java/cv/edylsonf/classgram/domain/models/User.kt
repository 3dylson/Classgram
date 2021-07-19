package cv.edylsonf.classgram.domain.models

data class User(
    var uid: String = "",
    var username: String = "",
    var email: String = "",
    var dateCreated: Date = Date()
    )
