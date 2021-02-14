package cv.edylsonf.classgram.network.api

internal const val BASE_URL = "https://api.twitter.com/"

internal const val BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAOq0MgEAAAAAOYIG6RuUZguoF4LjJmWjpWkfRd8%3DQJ6OsL3wU3qOourq7vZcn3ejRX7NIFyRZnIfE9OssPfwREpRsk"
internal const val USERS = "2/users"
internal const val TWEETS = "2/tweets?ids=1228393702244134912,1227640996038684673,1199786642791452673&tweet.fields=created_at&expansions=author_id&user.fields=created_at"
internal const val SEARCH = "$TWEETS/search/recent"

internal const val TEXTS_TWEET_ID = "tweet_id"
internal const val TEXTS_INCLUDE_TWEET = "include_tweet"