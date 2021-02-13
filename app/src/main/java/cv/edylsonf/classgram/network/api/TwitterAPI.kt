package cv.edylsonf.classgram.network.api

import cv.edylsonf.classgram.network.models.Search
import cv.edylsonf.classgram.network.models.Tweet
import retrofit2.Call
import retrofit2.http.*


interface TwitterAPI {

    @Headers("Authorization: Bearer $BEARER_TOKEN")
    @GET(TWEETS)
    fun getTweetsList(): Call<List<Tweet>>

    @Headers("Authorization: Bearer $BEARER_TOKEN")
    @GET(SEARCH)
    fun getTweetsText(@Query(TEXTS_INCLUDE_TWEET) id: String,
                      @Query(TEXTS_TWEET_ID) tweetId: String): Call<List<Search>>

    @Headers("Authorization: Bearer $BEARER_TOKEN")
    @POST(TWEETS)
    @FormUrlEncoded
    fun postTweets(@Field("text") text: String);

}