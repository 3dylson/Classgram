package cv.edylsonf.classgram

import android.app.Application
import cv.edylsonf.classgram.database.AppDatabase
import cv.edylsonf.classgram.database.repositories.TweetRepository
import cv.edylsonf.classgram.database.repositories.UserRepository

class ClassgramApplication : Application(){

    val database by lazy {AppDatabase.getDatabase(this)}
    val repositoryUser by lazy {UserRepository(database.userDao())}
    val repositoryTweet by lazy {TweetRepository(database.tweetDao())}
}

