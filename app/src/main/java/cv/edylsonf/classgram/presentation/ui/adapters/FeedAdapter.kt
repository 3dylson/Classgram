package cv.edylsonf.classgram.presentation.ui.adapters

/*
class FeedAdapter ( val clickAction: (Tweet) -> Unit)
    : ListAdapter<Tweet, FeedAdapter.MainViewHolder?>(DiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(inflater.inflate(R.layout.item_feed,parent, false))
    }



    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val tweet = getItem(position)
        holder.tweet.text = tweet.data.toString()
        holder.tweet.setOnClickListener {
            clickAction(tweet)
        }

        */
/*if (tweet.fav)
            holder.fav.setImageResource(R.drawable.ic_favorite)
        else
            holder.fav.setImageResource(R.drawable.ic_favorite_empty)

        holder.fav.setOnClickListener {
            favAction(tweet)
        }*//*

    }



    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {

        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet) =
            oldItem == newItem
    }


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tweet: TextView = itemView.findViewById(R.id.tv_tweet)
    }
}*/
