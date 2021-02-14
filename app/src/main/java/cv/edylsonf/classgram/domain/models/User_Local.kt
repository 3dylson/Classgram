package cv.edylsonf.classgram.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "User", indices = [Index("username", unique = true)
    /*, Index("user_tweet")],
foreignKeys = [ForeignKey(entity = Tweet::class,parentColumns = ["Tweet"],childColumns = ["user_tweet"])*/])
data class User(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="id")
    val id: String,

    @ColumnInfo(name="name")
    val name: String?,

    @ColumnInfo(name="username")
    val username: String?,
    /*@ColumnInfo(name="user_tweet")
    val Tweet: Tweet? = null,*/


)