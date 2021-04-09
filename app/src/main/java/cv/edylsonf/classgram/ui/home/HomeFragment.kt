package cv.edylsonf.classgram.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cv.edylsonf.classgram.R

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(R.layout.home_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate HomeFragment")
    }

}