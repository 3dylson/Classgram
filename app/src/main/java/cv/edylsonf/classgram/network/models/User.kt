package cv.edylsonf.classgram.network.models

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "created_at")
    val createdAt: String,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "username")
    val username: String
)