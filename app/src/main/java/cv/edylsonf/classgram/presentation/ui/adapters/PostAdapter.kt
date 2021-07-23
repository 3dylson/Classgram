package cv.edylsonf.classgram.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.domain.models.Post

class PostAdapter(val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {


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
            val text = itemView.findViewById<TextView>(R.id.tv_text)
            text.text = post.text
        }
    }
}