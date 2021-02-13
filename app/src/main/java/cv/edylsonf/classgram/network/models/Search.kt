package cv.edylsonf.classgram.network.models

import com.squareup.moshi.Json

data class Search(
    @field:Json(name = "tweet")
    val tweet: List<Tweet>,
    @field:Json(name = "id")
    val id: String,
)
