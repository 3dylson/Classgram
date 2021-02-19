package cv.edylsonf.classgram

import android.app.Application
import cv.edylsonf.classgram.network.AppDatabase

class ClassgramApplication : Application(){

    val database by lazy {AppDatabase.getDatabase(this)}
    /*val repositoryStudent by lazy {StudentRepository(database.studentDao())}
    val repositoryPost by lazy {PostRepository(database.postDao())}*/
}

