package cv.edylsonf.classgram.database.models

import com.squareup.moshi.Json

data class UserData(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "username")
    val username: String
)