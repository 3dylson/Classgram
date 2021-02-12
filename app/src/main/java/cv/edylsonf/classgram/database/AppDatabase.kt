package cv.edylsonf.classgram.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cv.edylsonf.classgram.database.dao.TweetDao
import cv.edylsonf.classgram.database.dao.UserDao
import cv.edylsonf.classgram.database.models.Tweet
import cv.edylsonf.classgram.database.models.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [User::class, Tweet::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun tweetDao(): TweetDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        private const val DB_NAME = "classgram_database"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(2)
    }

}