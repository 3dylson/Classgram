package cv.edylsonf.classgram.network.dao

import androidx.room.*
import cv.edylsonf.classgram.domain.models.Post



@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Update
    fun refresh(post: Post)

    @Query("SELECT * FROM Post")
    fun getAllTweets(): List<Post>

    @Delete
    fun deleteTweet(post: Post)

    /*@Query("SELECT * FROM Tweet WHERE text LIKE :text")
    fun findByText(text: String, text: String): Tweet*/



}