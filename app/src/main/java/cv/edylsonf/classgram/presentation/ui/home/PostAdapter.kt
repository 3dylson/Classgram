package cv.edylsonf.classgram.presentation.ui.home

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ItemFeedBinding
import cv.edylsonf.classgram.domain.models.Post
import cv.edylsonf.classgram.presentation.ui.adapters.FirestoreAdapter

open class PostAdapter(query: Query, private val listener: OnPostSelectedListener) :
    FirestoreAdapter<PostAdapter.ViewHolder>(query) {

    interface OnPostSelectedListener {
        fun onPostSelected(post: DocumentSnapshot)
    }

    private lateinit var bindingPost: ItemFeedBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
        /*bindingPost = ItemFeedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bindingPost.root)*/
        return ViewHolder(ItemFeedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    //override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)
    }



    class ViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            snapshot: DocumentSnapshot,
            listener: OnPostSelectedListener?
        ) {
            val post = snapshot.toObject<Post>() ?: return //elvis operator
            val resources = binding.root.resources

            var hasImagePost = false
            if (post.imageUrl != null) {
                hasImagePost = true
            }
            Glide.with(binding.avatar.context)
                .load(post.user?.profilePic)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(itemView.findViewById(R.id.avatar))
            binding.tvName.text = post.user?.firstLastName
            post.user?.headLine?.let { binding.tvField.hint = it } ?: run { binding.tvField.hint = "@"+post.user?.username }
            binding.tvTimeAgo.hint = DateUtils.getRelativeTimeSpanString(post.creationTime!!)
            binding.tvText.text = post.text
            post.upCount?.let { binding.starCount.text = it.toString() } ?: run { binding.starCount.text = "0" }
            post.comments?.let { binding.commNum.text = it.size.toString() } ?: run { binding.commNum.text = "0" }
            if (hasImagePost) {
                Glide.with(binding.tvPostImage.context)
                    .load(post.imageUrl)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.findViewById(R.id.tv_postImage))
            }
            else {
                binding.tvPostImage.visibility = View.GONE
            }

            // Click listener
            binding.root.setOnClickListener {
                listener?.onPostSelected(snapshot)
            }

        }
    }


    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            var hasImagePost = false
            if (post.imageUrl != null) {
                hasImagePost = true
            }
            Glide.with(context)
                .load(post.user?.profilePic)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
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
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.findViewById(R.id.tv_postImage))
            }
            else {
                bindingPost.tvPostImage.visibility = View.GONE
            }


        }
    }*/


}