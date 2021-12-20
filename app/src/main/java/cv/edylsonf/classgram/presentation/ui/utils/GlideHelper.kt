package cv.edylsonf.classgram.presentation.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class GlideHelper @Inject constructor(@ApplicationContext val context: Context) {

    @Composable
    fun fetchImage(url: String): Painter? {
        var image by remember(url) { mutableStateOf<Painter?>(null) }

        val options: RequestOptions = RequestOptions().autoClone().diskCacheStrategy(
            DiskCacheStrategy.ALL
        )

        SideEffect {

            val target = object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    image = BitmapPainter(resource.asImageBitmap())
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            }

            val glide = Glide.with(context)
            glide.asBitmap()
                .load(url)
                .apply(options)
                .into(target)


            //cancel any pendent request with Dispose
            DisposableEffectScope().onDispose {
                glide.clear(target)
            }

        }

        return image
    }

    /*@Composable
    fun loadPicture(url: String, placeholder: Painter? = null): Painter? {

        var state by remember {
            mutableStateOf(placeholder)
        }

        val options: RequestOptions = RequestOptions().autoClone().diskCacheStrategy(
            DiskCacheStrategy.ALL
        )
        val context = LocalContext.current
        val result = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(p: Drawable?) {
                state = placeholder
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?,
            ) {
                state = BitmapPainter(resource.asImageBitmap())
            }
        }
        try {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(result)
        } catch (e: Exception) {
            // Can't use LocalContext in Compose Preview
        }
        return state
    }*/

}
