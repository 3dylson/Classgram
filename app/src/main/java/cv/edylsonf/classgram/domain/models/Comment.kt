package cv.edylsonf.classgram.domain.models

data class Comment(
    var id: String? = null,
    var parent: Comment? = null,
    var text: String? = null,
    var user: UserPostDetail? = null,
    var isTheAnswer: Boolean? = false
)
