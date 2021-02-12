package cv.edylsonf.classgram.database.dao

import androidx.room.*
import cv.edylsonf.classgram.database.models.Tweet

@Dao
interface TweetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweet: Tweet)

    @Update
    fun refresh(tweet: Tweet)

    @Query("SELECT * FROM Tweet")
    fun getAllTweets(): List<Tweet>

    @Delete
    fun deleteTweet(tweet: Tweet)

    @Query("SELECT * FROM Tweet WHERE text LIKE :text")
    fun findByText(data: String, text: String): Tweet



}