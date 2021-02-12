package cv.edylsonf.classgram.presentation.views

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import cv.edylsonf.classgram.presentation.utils.Navigator

/**
 * Models the screens in the app and any arguments they require.
 */
sealed class Destination : Parcelable {

    @Parcelize
    object Home : Destination()

    @Immutable
    @Parcelize
    data class SnackDetail(val snackId: Long) : Destination()

}

/**
 * Models the navigation actions in the app.
 */
class Actions(navigator: Navigator<Destination>){
    val selectSnack: (Long) -> Unit = { snackId: Long ->
        navigator.navigate(Destination.SnackDetail(snackId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}