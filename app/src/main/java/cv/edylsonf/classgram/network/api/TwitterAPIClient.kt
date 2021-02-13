package cv.edylsonf.classgram.network.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cv.edylsonf.classgram.network.models.Search

import cv.edylsonf.classgram.network.models.Tweet
import cv.edylsonf.classgram.network.repositories.DataRetrieved
import cv.edylsonf.classgram.network.repositories.DataSearched
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
                Log.e(TAG, "Unable to get tweet's text. Error: ${t.message}")
                listener.onDataSearchedFailed()
            }
        })
    }

    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


/** Retrofit to make REST requests to the web service and MOSHI to handle the deserialization of the
    returned JSON to Kotlin data objects */

    private fun setup(): TwitterAPI {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create()
    }




}