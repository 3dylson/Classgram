package cv.edylsonf.classgram.network.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "User", indices = [Index("username", unique = true)
                                      /*, Index("user_tweet")],
        foreignKeys = [ForeignKey(entity = Tweet::class,parentColumns = ["Tweet"],childColumns = ["user_tweet"])*/])
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") @NonNull
    val id: Int?, /*String?*/

    @ColumnInfo(name="name")
    val name: String?,

    @ColumnInfo(name="username")
    val username: String?,
    /*@ColumnInfo(name="user_tweet")
    val Tweet: Tweet? = null,*/


)