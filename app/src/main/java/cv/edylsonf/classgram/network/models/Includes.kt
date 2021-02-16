package cv.edylsonf.classgram.network.models

import androidx.room.ColumnInfo
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.squareup.moshi.Json


data class Includes(

    val users: List<User>,

)

