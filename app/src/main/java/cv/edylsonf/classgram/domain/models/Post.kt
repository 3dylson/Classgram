package cv.edylsonf.classgram.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import cv.edylsonf.classgram.network.models.Data
import cv.edylsonf.classgram.network.models.Includes

@Entity(tableName = "Post")
data class Post(
        @ColumnInfo(name = "data")
        val `data`: List<Data>,
        @ColumnInfo(name = "includes")
        val includes: Includes
)