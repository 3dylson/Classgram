package cv.edylsonf.classgram.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cv.edylsonf.classgram.database.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user:User)

    @Query("SELECT * FROM User WHERE id=:id ")
    fun findUserById(id: String): LiveData<User>

    @Query("DELETE FROM User WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM User")
    fun deleteAll()



}