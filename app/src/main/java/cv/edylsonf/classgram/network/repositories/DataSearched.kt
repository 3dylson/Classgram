package cv.edylsonf.classgram.network.repositories

import cv.edylsonf.classgram.network.models.Search

// Call Back
interface DataSearched {

    fun onDataSearchedSuccess(search: List<Search>)

    fun onDataSearchedFailed()
}