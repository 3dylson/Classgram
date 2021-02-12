package cv.edylsonf.classgram.database.repositories

import cv.edylsonf.classgram.database.models.Search

// Call Back
interface DataSearched {

    fun onDataSearchedSuccess(search: List<Search>)

    fun onDataSearchedFailed()
}