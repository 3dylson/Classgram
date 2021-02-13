package cv.edylsonf.classgram.network.repositories

import cv.edylsonf.classgram.network.AppDatabase
import cv.edylsonf.classgram.network.dao.TweetDao
import cv.edylsonf.classgram.network.models.Tweet

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
