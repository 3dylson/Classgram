package cv.edylsonf.classgram.database.dao;



import androidx.room.Dao;
import androidx.room.Query;

import cv.edylsonf.classgram.database.models.User;

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUsers() List<User>


}



