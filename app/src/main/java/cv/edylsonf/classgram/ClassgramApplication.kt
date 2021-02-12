package cv.edylsonf.classgram

import android.app.Application
import cv.edylsonf.classgram.database.AppDatabase
import cv.edylsonf.classgram.database.repositories.UserRepository

class ClassgramApplication : Application(){

    val database by lazy {AppDatabase.getDatabase(this)}
    val repository by lazy {UserRepository(database.userDao())}
}