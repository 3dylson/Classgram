package cv.edylsonf.classgram.network.repositories.cb

import cv.edylsonf.classgram.network.models.User

interface UserDataRetrieved {

    fun onUserDataFetchedSuccess(users: List<User>)

    fun onUserDataFetchedFailed()
}