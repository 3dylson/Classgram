package cv.edylsonf.classgram.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.REQUEST_IMAGE_CAPTURE
import cv.edylsonf.classgram.databinding.FragmentHomeBinding

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //binding.toolbar.title = getString(R.string.home_title)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_action_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cam -> openNativeCamera()
        }
        return true
    }

    private fun openNativeCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }



}