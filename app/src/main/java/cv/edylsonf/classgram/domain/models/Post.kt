package cv.edylsonf.classgram.domain.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cv.edylsonf.classgram.network.models.Data

@Entity(tableName = "Post")
data class Post(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "data")
        val `data`: List<Data>,

)