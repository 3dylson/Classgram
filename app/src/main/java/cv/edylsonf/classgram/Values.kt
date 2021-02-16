package cv.edylsonf.classgram

import android.os.Build.MANUFACTURER
import android.os.Build.MODEL

const val ACTIVITY_REQUEST = "activity.request"
const val EXTRA_USERNAME = "extra.username"

const val INVALID_ID = -1

fun deviceName() = "$MODEL-$MANUFACTURER"

class Values {
    companion object {
        @JvmStatic
        fun students() {}
        @JvmStatic
        fun posts() {}
    }

}