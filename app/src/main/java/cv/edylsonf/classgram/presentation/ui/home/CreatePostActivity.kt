package cv.edylsonf.classgram.presentation.ui.home

import android.os.Bundle
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.presentation.ui.utils.BaseActivity

class CreatePostActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Classgram)
        setContentView(R.layout.activity_create_post)
    }
}