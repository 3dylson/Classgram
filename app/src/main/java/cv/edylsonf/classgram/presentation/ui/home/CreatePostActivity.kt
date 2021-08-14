package cv.edylsonf.classgram.presentation.ui.home

import android.os.Bundle
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityCreatePostBinding
import cv.edylsonf.classgram.domain.models.User
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "CreatePostActivity"

class CreatePostActivity : BaseActivity() {

    private var signedInUser: User? = null

    private lateinit var binding: ActivityCreatePostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        binding.root.setPadding(16)
        setContentView(binding.root)

        supportActionBar?.title = "Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close)

        with(binding) {
            captureFab.setOnClickListener { openCam() }
            imgPicker.setOnClickListener { openGallery() }
        }
    }

    private fun openGallery() {
        TODO("Not yet implemented")
    }

    private fun openCam() {
        TODO("Not yet implemented")
    }
}