package cv.edylsonf.classgram.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.network.models.Tweet

class FeedAdapter internal constructor(private val tweet: List<Tweet>) : RecyclerView.Adapter<FeedAdapter.MainViewHolder?>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(inflater.inflate(R.layout.item_feed,parent, false))
    }

    override fun getItemCount(): Int {
        return tweet.size
    }

    override fun onBindViewHolder(holder: FeedAdapter.MainViewHolder, position: Int) {
        val feed = tweet[position]
        holder.tweetText.text = feed.text
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tweetText = itemView.tv_tweet!!
    }
}