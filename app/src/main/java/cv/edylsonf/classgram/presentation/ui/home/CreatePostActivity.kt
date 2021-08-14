package cv.edylsonf.classgram.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import cv.edylsonf.classgram.PICK_PHOTO_CODE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityCreatePostBinding
import cv.edylsonf.classgram.domain.models.User
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

private const val TAG = "CreatePostActivity"

class CreatePostActivity : BaseActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    private var signedInUser: User? = null
    private var photoUri: Uri? = null
    private lateinit var database: FirebaseFirestore
    private lateinit var storageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Create Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close)

        with(binding) {
            captureFab.setOnClickListener { openCam() }
            imgPicker.setOnClickListener { openGallery() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_post_action_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.submitPost) {
            // ...
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openGallery() {
        Log.i(TAG, "Open up image picker on device")
        val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        imagePickerIntent.type = "image/*"
        if (imagePickerIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
        }
    }

    private fun openCam() {
        TODO("Not yet implemented")
    }
}