package cv.edylsonf.classgram.presentation.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import cv.edylsonf.classgram.PICK_PHOTO_CODE
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityCreatePostBinding
import cv.edylsonf.classgram.domain.models.User
import cv.edylsonf.classgram.presentation.ui.MainActivity
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
        setProgressBar(binding.postLoading)

        storageReference = FirebaseStorage.getInstance().reference
        database = Firebase.firestore


        setup()

        with(binding) {
            captureFab.setOnClickListener { openCam() }
            imgPicker.setOnClickListener { openGallery() }
            discardImg.setOnClickListener { discardSelectedImg() }
        }
    }

    private fun discardSelectedImg() {
        binding.discardImg.visibility = View.GONE
        binding.imgPost.setImageResource(0)
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
                binding.discardImg.visibility = View.VISIBLE
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
            actionBarMenu?.findItem(R.id.submitPost)?.isEnabled = false
            showProgressBar()

            //TODO mentions and tags function
            if (photoUri == null) {
                val post = hashMapOf(
                    "text" to binding.postText.text.toString(),
                    "imageUrl" to null,
                    "creationTime" to System.currentTimeMillis(),
                    "comments" to null,
                    "mentions" to null,
                    "upCount" to 0,
                    "ups" to null,
                    "tags" to null,
                    "user" to signedInUser
                )
                database.collection("posts").add(post)
                    .addOnCompleteListener { postCreationTask ->
                        actionBarMenu?.findItem(R.id.submitPost)?.isEnabled = true
                        hideProgressBar()
                        if (!postCreationTask.isSuccessful) {

                            Log.e(
                                TAG,
                                "Exception during Firebase operations",
                                postCreationTask.exception
                            )
                            Toast.makeText(
                                this,
                                "Oops... Something went wrong!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.postText.text.clear()
                            discardSelectedImg()
                            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
            } else {
                val photoUploadUri = photoUri as Uri
                val photoReference = storageReference.child("images/post_photos/${System.currentTimeMillis()}-photo.jpg")
                // Upload photo to Firebase Storage
                photoReference.putFile(photoUploadUri)
                    .continueWithTask { photoUploadTask ->
                        Log.i(TAG, "uploaded bytes: ${photoUploadTask.result?.bytesTransferred}")
                        // Retrieve image url of the uploaded image
                        photoReference.downloadUrl
                    }.continueWithTask { downloadUrlTask ->
                        // Create a post hashMap with the image URL and add that to the posts collection
                        val post = hashMapOf(
                            "text" to binding.postText.text.toString(),
                            "imageUrl" to downloadUrlTask.result.toString(),
                            "creationTime" to System.currentTimeMillis(),
                            "comments" to null,
                            "mentions" to null,
                            "upCount" to 0,
                            "ups" to null,
                            "tags" to null,
                            "user" to signedInUser)
                        database.collection("posts").add(post)
                    }.addOnCompleteListener { postCreationTask ->
                        actionBarMenu?.findItem(R.id.submitPost)?.isEnabled = true
                        hideProgressBar()
                        if (!postCreationTask.isSuccessful) {
                            Log.e(
                                TAG,
                                "Exception during Firebase operations",
                                postCreationTask.exception
                            )
                            Toast.makeText(
                                this,
                                "Oops... Something went wrong! ",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.postText.text.clear()
                            discardSelectedImg()
                            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
            }
        }
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