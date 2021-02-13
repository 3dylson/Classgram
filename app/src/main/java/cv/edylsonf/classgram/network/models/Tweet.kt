package cv.edylsonf.classgram.network.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "Tweet")
data class Tweet(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="id")
    val id: String,
    @ColumnInfo(name="text") @Json(name = "text")
    val text: String? = null,
)