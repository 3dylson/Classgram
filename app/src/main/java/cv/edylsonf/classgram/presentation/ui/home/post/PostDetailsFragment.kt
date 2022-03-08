package cv.edylsonf.classgram.presentation.ui.home.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cv.edylsonf.classgram.databinding.FragmentPostDetailsBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment

class PostDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentPostDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(layoutInflater)
        toolbar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }
}