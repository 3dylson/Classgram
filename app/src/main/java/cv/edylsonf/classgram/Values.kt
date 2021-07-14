package cv.edylsonf.classgram

import android.os.Build.MANUFACTURER
import android.os.Build.MODEL

const val ACTIVITY_REQUEST = "activity.request"
const val EXTRA_EMAIL = "extra.email"
const val EXTRA_TAB_TITLE = "extra_tab_title"
const val EXTRA_TAB_SELECTED = "extra_tab_selected"
const val REQUEST_IMAGE_CAPTURE = 100
const val REQUEST_READ_STORAGE = 500

const val INVALID_ID = -1

fun deviceName() = "$MODEL-$MANUFACTURER"

class Values {
    companion object {
        @JvmStatic
        fun users() {}
        @JvmStatic
        fun posts() {}
    }

}