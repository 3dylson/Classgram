package cv.edylsonf.classgram.presentation.ui.utils

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class BaseFragment : Fragment() {

    private var progressBar: ProgressBar? = null

    val toolbar: ActionBar?
        get() = (requireActivity() as AppCompatActivity).supportActionBar

    val uid: String
        get() = Firebase.auth.currentUser!!.uid

    fun setProgressBar(resId: Int) {
        progressBar = view?.findViewById(resId)
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }


    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }
}