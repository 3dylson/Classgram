package cv.edylsonf.classgram.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Tweet")
data class Tweet(
    @ColumnInfo(name="data")
    val `data`: List<TweetData>
)