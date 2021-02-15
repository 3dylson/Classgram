package cv.edylsonf.classgram.network.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cv.edylsonf.classgram.domain.models.Student


@Dao
interface StudentDao {

    @Query("SELECT * FROM Student")
    fun getUsers(): List<Student>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(student:Student)

    @Query("SELECT * FROM Student WHERE id=:id ")
    fun findUserById(id: String): LiveData<Student>

    @Query("DELETE FROM Student WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM Student")
    fun deleteAll()



}