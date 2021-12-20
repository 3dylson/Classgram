package cv.edylsonf.classgram.presentation.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import cv.edylsonf.classgram.EXTRA_TAB_TITLE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment


class ScheduleFragment : BaseFragment() {

    private var fragTitle: String? = null
    private var toolbar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragTitle = it.getString(EXTRA_TAB_TITLE)
        }

        toolbar = (activity as AppCompatActivity).supportActionBar

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //toolbar?.title = fragTitle

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onResume() {
        super.onResume()
        //toolbar?.title = fragTitle
    }
}