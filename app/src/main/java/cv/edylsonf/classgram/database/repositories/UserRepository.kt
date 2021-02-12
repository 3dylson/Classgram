package cv.edylsonf.classgram.database.repositories

import cv.edylsonf.classgram.database.AppDatabase
import cv.edylsonf.classgram.database.dao.UserDao
import cv.edylsonf.classgram.database.models.User


class UserRepository(private val userDao: UserDao) {

    fun deleteById(id: String, onLoaded: (Unit) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                userDao.deleteById(id)
                onLoaded(Unit)
            }
    }

    fun getUsers(onLoaded: (List<User>) -> Unit) {
        AppDatabase
            .databaseWriteExecutor
            .execute {
                onLoaded(userDao.getUsers())
            }
    }

    fun insert(user: User) {
        AppDatabase
            .databaseWriteExecutor
            .execute {
                userDao.insert(user)
            }
    }


}