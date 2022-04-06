package cv.edylsonf.classgram.presentation.ui.schedule.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cv.edylsonf.classgram.databinding.FragmentAgendaBinding
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment

class AgendaFragment : BaseFragment() {

    private lateinit var binding: FragmentAgendaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgendaBinding.inflate(layoutInflater)
        toolbar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

}