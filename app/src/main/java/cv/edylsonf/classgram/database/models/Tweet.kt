package cv.edylsonf.classgram.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tweet")
data class Tweet(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="id")
    val id: String,
    @ColumnInfo(name="text")
    val text: String?,
)