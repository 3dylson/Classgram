package cv.edylsonf.classgram.network.repositories.cb

import cv.edylsonf.classgram.network.models.Search

// Call Back
interface DataSearched {

    fun onDataSearchedSuccess(search: List<Search>)

    fun onDataSearchedFailed()
}