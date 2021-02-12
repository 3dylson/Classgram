package cv.edylsonf.classgram.database.models

import androidx.room.*


@Entity(tableName = "User", indices = [Index("username", unique = true)
                                      /*, Index("user_tweet")],
        foreignKeys = [ForeignKey(entity = Tweet::class,parentColumns = ["Tweet"],childColumns = ["user_tweet"])*/])
data class User(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="id")
    val id: String,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="username")
    val username: String,
    /*@ColumnInfo(name="user_tweet")
    val Tweet: Tweet? = null,*/


)