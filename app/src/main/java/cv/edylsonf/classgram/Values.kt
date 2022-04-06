package cv.edylsonf.classgram

import android.os.Build.MANUFACTURER
import android.os.Build.MODEL

const val ACTIVITY_REQUEST = "activity.request"
const val EXTRA_EMAIL = "extra.email"
const val EXTRA_TAB_TITLE = "extra_tab_title"
const val EXTRA_TAB_SELECTED = "extra_tab_selected"
const val REQUEST_IMAGE_CAPTURE = 100
const val REQUEST_READ_STORAGE = 500
const val REQUEST_CAMERA = 0
const val PROFILE_TAB_PAGES = 2
const val PICK_PHOTO_CODE = 1234
// TODO change this to project image
const val DEFAULT_PROFILE_PIC = "https://firebasestorage.googleapis.com/v0/b/classgram-app.appspot.com/o/images%2Fprofile_photos%2Fprofile_default_pic.jpg?alt=media&token=8eb1aa5d-ad85-4679-9924-1caaa3e47bb4"
const val SIGNED_IN_USER = "signedInUser"
const val CARD_TYPE = "cardType"
const val CALENDER_CARD = "calendar_card"
const val AGENDA_CARD = "agenda_card"
const val TIMETABLE_CARD = "timetable_card"
const val DATE_PICKER_DIALOG = "Datepickerdialog"
const val TIME_PICKER_DIALOG = "Timepickerdialog"

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