package cv.edylsonf.classgram.network.dao

import androidx.room.*
import cv.edylsonf.classgram.network.models.Tweet


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

    /*@Query("SELECT * FROM Tweet WHERE text LIKE :text")
    fun findByText(text: String, text: String): Tweet*/



}