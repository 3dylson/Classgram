package cv.edylsonf.classgram.presentation.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import cv.edylsonf.classgram.network.models.Data


class Converters {



    @TypeConverter
    fun listToJson(value: List<Data>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Data>::class.java).toList()


}

