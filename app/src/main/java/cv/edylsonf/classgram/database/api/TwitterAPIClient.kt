package cv.edylsonf.classgram.database.api

import android.util.Log
import cv.edylsonf.classgram.database.models.Search

import cv.edylsonf.classgram.database.models.Tweet
import cv.edylsonf.classgram.database.repositories.DataRetrieved
import cv.edylsonf.classgram.database.repositories.DataSearched
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
                Log.e(TAG, "Unable to get tweets. Error: ${t.message}")
                listener?.onDataFetchedFailed()
            }

        })

    }

    fun getTwitterText(twitterId: String, listener: DataSearched){
        apiTwitter.getTweetsText("1", twitterId).enqueue(object : Callback<List<Search>> {

            override fun onResponse(call: Call<List<Search>>, response: Response<List<Search>>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "code: " + response.code())
                    return
                }
                listener.onDataSearchedSuccess(response.body()!!)

            }

            override fun onFailure(call: Call<List<Search>>, t: Throwable) {
                Log.e(TAG, "Unable to get tweet text. Error: ${t.message}")
                listener.onDataSearchedFailed()
            }
        })
    }



    private fun setup(): TwitterAPI {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create()
    }




}