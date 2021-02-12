package cv.edylsonf.classgram.database.repositories

import cv.edylsonf.classgram.database.AppDatabase
import cv.edylsonf.classgram.database.dao.TweetDao
import cv.edylsonf.classgram.database.models.Tweet

class TweetRepository (private val tweetDao: TweetDao){

    fun deleteTweet(tweet: Tweet, onLoaded: (Unit) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                tweetDao.deleteTweet(tweet)
                onLoaded(Unit)
            }
    }

    fun getAllTweets(onLoaded: (List<Tweet>) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                onLoaded(tweetDao.getAllTweets())
            }
    }

    fun insert(tweet: Tweet){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                tweetDao.insert(tweet)
            }
    }




}
