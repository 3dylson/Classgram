package cv.edylsonf.classgram.presentation.ui.adapters

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ItemFeedBinding
import cv.edylsonf.classgram.domain.models.Post

class PostAdapter(val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var bindingPost: ItemFeedBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
        bindingPost = ItemFeedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bindingPost.root)
    }

    override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            var hasImagePost = false
            if (post.imageUrl != null) {
                hasImagePost = true
            }
            Glide.with(context)
                .load(post.user?.profilePic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(itemView.findViewById(R.id.avatar))
            bindingPost.tvName.text = post.user?.firstLastName
            post.user?.headLine?.let { bindingPost.tvField.hint = it } ?: run { bindingPost.tvField.hint = "@"+post.user?.username }
            bindingPost.tvTimeAgo.hint = DateUtils.getRelativeTimeSpanString(post.creationTime!!)
            bindingPost.tvText.text = post.text
            post.upCount?.let { bindingPost.starCount.text = it.toString() } ?: run { bindingPost.starCount.text = "0" }
            post.comments?.let { bindingPost.commNum.text = it.size.toString() } ?: run { bindingPost.commNum.text = "0" }
            if (hasImagePost) {
                Glide.with(context)
                    .load(post.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.findViewById(R.id.tv_postImage))
            }
            else {
                bindingPost.tvPostImage.visibility = View.GONE
            }


        }
    }
}