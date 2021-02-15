package cv.edylsonf.classgram.network.repositories

import cv.edylsonf.classgram.domain.models.Student
import cv.edylsonf.classgram.network.AppDatabase
import cv.edylsonf.classgram.network.dao.StudentDao
import cv.edylsonf.classgram.network.models.User


class StudentRepository(private val studentDao: StudentDao) {

    fun deleteById(id: String, onLoaded: (Unit) -> Unit){
        AppDatabase
            .databaseWriteExecutor
            .execute {
                studentDao.deleteById(id)
                onLoaded(Unit)
            }
    }

    fun getUsers(onLoaded: (List<Student>) -> Unit) {
        AppDatabase
            .databaseWriteExecutor
            .execute {
                onLoaded(studentDao.getUsers())
            }
    }

    fun insert(student: Student) {
        AppDatabase
            .databaseWriteExecutor
            .execute {
                studentDao.insert(student)
            }
    }


}