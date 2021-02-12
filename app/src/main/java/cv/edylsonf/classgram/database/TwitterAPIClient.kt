package cv.edylsonf.classgram.database

import android.util.Log

import cv.edylsonf.classgram.database.models.Tweet
import cv.edylsonf.classgram.database.repositories.DataRetrieved
import retrofit2.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "TwitterApi"

object TwitterAPIClient {

    private val apiTwitter by lazy{
        setup()
    }

    fun getListOfTweets(listener: DataRetrieved? = null) {
        apiTwitter.getTweetsList().enqueue(object : Callback<List<Tweet>> {

            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "code: " + response.code())
                    return
                }
                listener?.onDataFetchedSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Log.e(TAG, "Unable to get tweets texts. Error: ${t.message}")
                listener?.onDataFetchedFailed()
            }

        })

    }




}