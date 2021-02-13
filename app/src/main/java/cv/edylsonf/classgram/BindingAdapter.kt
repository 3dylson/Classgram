package cv.edylsonf.classgram

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import cv.edylsonf.classgram.presentation.viewmodels.TwitterAPIStatus

@BindingAdapter("twitterAPIStatus")
fun bindStatus(statusImageView: ImageView, status: TwitterAPIStatus?){
    when (status) {
        TwitterAPIStatus.LOADING -> {
            statusImageView.visibility= View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TwitterAPIStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TwitterAPIStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}