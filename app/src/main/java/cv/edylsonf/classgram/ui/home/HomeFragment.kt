package cv.edylsonf.classgram.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import cv.edylsonf.classgram.R

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate HomeFragment")
    }

}