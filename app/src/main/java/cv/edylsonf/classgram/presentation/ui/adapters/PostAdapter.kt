package cv.edylsonf.classgram.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ItemFeedBinding
import cv.edylsonf.classgram.domain.models.Post

class PostAdapter(val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var bindingPost: ItemFeedBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            /*val text = itemView.findViewById<TextView>(R.id.tv_text)
            text.text = post.text*/
            Glide.with(context)
                .load(post.user?.profilePic)
                .circleCrop()
                .into(bindingPost.avatar)
        }
    }
}