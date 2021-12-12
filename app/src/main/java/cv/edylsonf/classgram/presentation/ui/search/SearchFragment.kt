package cv.edylsonf.classgram.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.FragmentSearchBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment


class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seacrh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}