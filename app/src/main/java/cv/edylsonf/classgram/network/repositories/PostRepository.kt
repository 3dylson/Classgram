package cv.edylsonf.classgram.network.repositories

import cv.edylsonf.classgram.domain.models.Post
import cv.edylsonf.classgram.network.AppDatabase
import cv.edylsonf.classgram.network.dao.PostDao


class PostRepository (private val postDao: PostDao){

    fun deleteTweet(post: Post, onLoaded: (Unit) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                postDao.deleteTweet(post)
                onLoaded(Unit)
            }
    }

    fun getAllTweets(onLoaded: (List<Post>) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                onLoaded(postDao.getAllTweets())
            }
    }

    fun insert(post: Post){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                postDao.insert(post)
            }
    }




}
