package cv.edylsonf.classgram.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class TwitterAPIStatus {LOADING, ERROR, DONE}

class TweetViewModel : ViewModel() {

    private val _status = MutableLiveData<TwitterAPIStatus>()

    val status: LiveData<TwitterAPIStatus>
    get() = _status
}