package cv.edylsonf.classgram.network.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tweet")
data class Tweet(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="id") @NonNull
    val id: String,
    @ColumnInfo(name="text")
    val text: String?,
)