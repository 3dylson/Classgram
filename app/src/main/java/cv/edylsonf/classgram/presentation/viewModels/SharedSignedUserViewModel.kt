package cv.edylsonf.classgram.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cv.edylsonf.classgram.domain.models.UserPostDetail

class SharedSignedUserViewModel : ViewModel() {
    val signedInUser = MutableLiveData<UserPostDetail>()

    fun selectUser(user: UserPostDetail) {
        signedInUser.value = user
    }


}