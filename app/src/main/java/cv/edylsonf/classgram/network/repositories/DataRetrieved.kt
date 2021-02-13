package cv.edylsonf.classgram.network.repositories

import cv.edylsonf.classgram.network.models.Tweet


// Call Back
interface DataRetrieved {

    fun onDataFetchedSuccess(tweets: List<Tweet>)

    fun onDataFetchedFailed()
}