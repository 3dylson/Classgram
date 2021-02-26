package cv.edylsonf.classgram

import android.os.Build.MANUFACTURER
import android.os.Build.MODEL

const val ACTIVITY_REQUEST = "activity.request"
const val EXTRA_EMAIL = "extra.email"

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