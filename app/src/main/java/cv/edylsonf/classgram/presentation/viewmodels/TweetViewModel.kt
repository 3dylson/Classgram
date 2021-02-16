package cv.edylsonf.classgram.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cv.edylsonf.classgram.domain.models.Post
import cv.edylsonf.classgram.network.api.TwitterAPIClient
import cv.edylsonf.classgram.network.models.Tweet
import cv.edylsonf.classgram.network.repositories.PostRepository
import cv.edylsonf.classgram.network.repositories.cb.DataRetrieved

enum class TwitterAPIStatus {LOADING, ERROR, DONE}

private const val TAG = "TweetViewModel"

class TweetViewModel( private val repository: PostRepository) : ViewModel(), DataRetrieved {

    private val _postViewModel = MutableLiveData<List<Tweet>>()
    val postLiveData = _postViewModel

    private var postLoaded = emptyList<Tweet>()

    fun loadPost(){
        _status.value = TwitterAPIStatus.LOADING
        try{
        TwitterAPIClient.getListOfTweets(this)
            _status.value = TwitterAPIStatus.DONE
        }catch (e: Exception) {
            _status.value = TwitterAPIStatus.ERROR
        }
    }

    private val _status = MutableLiveData<TwitterAPIStatus>()

    val status: LiveData<TwitterAPIStatus>
    get() = _status


    //region DataRetrieved

    override fun onDataFetchedSuccess(tweets: List<Tweet>) {
        Log.e(TAG, "onDataFetched Success | ${tweets.size} new tweets")
        postLoaded = tweets
        updatePosts()
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG,"Unable to retrieve new data")
        _postViewModel.postValue(emptyList())
    }

    //endregion DataRetrieved

    private fun updatePosts(){
        repository.getAllTweets { posts ->
            val postsIDs = posts.map{it.id}
            postLoaded.map {
                if (postsIDs.contains(it.id))
                    it.copy() // fav = true
                else
                    it
            }.let {
                _postViewModel.postValue(it)
            }
        }
    }

    private fun tweetToPost(tweet: Tweet): Post {
        return Post(
            id = tweet.id,
            data = tweet.data
        )
    }
}
/**
 * Simple ViewModel factory that provides the Tweet and context to the ViewModel.
 */
class TweetViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TweetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TweetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}