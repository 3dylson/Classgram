package cv.edylsonf.classgram.database.repositories

import cv.edylsonf.classgram.database.models.Tweet

interface DataRetrieved {

    fun onDataFetchedSuccess(tweets: List<Tweet>)

    fun onDataFetchedFailed()
}