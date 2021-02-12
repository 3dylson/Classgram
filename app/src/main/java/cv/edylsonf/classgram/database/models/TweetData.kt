package cv.edylsonf.classgram.database.models

import com.squareup.moshi.Json

data class TweetData(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "text")
    val text: String
)