package cv.edylsonf.classgram.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "User")
data class User(
    @ColumnInfo
    val `data`: List<UserData>
)