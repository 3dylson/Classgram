package cv.edylsonf.classgram.presentation.ui.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cv.edylsonf.classgram.presentation.widget.SpaceDecoration

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("fabVisibility")
fun fabVisibility(fab: FloatingActionButton, visible: Boolean) {
    if (visible) fab.show() else fab.hide()
}

/** Set text on a [TextView] from a string resource. */
@BindingAdapter("android:text")
fun setText(view: TextView, @StringRes resId: Int) {
    if (resId == 0) {
        view.text = null
    } else {
        view.setText(resId)
    }
}

@BindingAdapter("itemSpacing")
fun itemSpacing(view: RecyclerView, dimen: Float) {
    val space = dimen.toInt()
    if (space > 0) {
        view.addItemDecoration(SpaceDecoration(space, space, space, space))
    }
}