package cv.edylsonf.classgram.network.models

import com.squareup.moshi.Json

data class Tweet(
    @field:Json(name = "data")
    val `data`: List<Data>,
    @field:Json(name = "includes")
    val includes: Includes
)