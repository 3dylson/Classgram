package cv.edylsonf.classgram.presentation.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    private var actionBarMenu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference

        setup()

        with(binding) {
            captureFab.setOnClickListener { openCam() }
            imgPicker.setOnClickListener { openGallery() }
        }
    }

    private fun setup() {
        signedInUser = intent?.getParcelableExtra("signedInUser")
        Glide.with(this)
            .load(signedInUser?.profilePic)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(binding.createPostUserAvatar)
        binding.createPostUsername.text = signedInUser?.firstLastName

        supportActionBar?.title = "Create Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                binding.imgPost.setImageURI(photoUri)
            } else {
                Toast.makeText(this, "Image picker action canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_post_action_menu,menu)
        actionBarMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.submitPost) {
            handlePostSubmit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handlePostSubmit() {
        if (binding.postText.text.isBlank()) {
            binding.postText.error = "Don't be shy..."
            return
        }
        else {
            binding.postText.error = null
        }

        actionBarMenu?.findItem(R.id.submitPost)?.isEnabled = false

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