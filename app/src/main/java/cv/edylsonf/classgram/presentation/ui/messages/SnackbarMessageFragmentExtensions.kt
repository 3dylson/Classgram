package cv.edylsonf.classgram.presentation.ui.messages

import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import cv.edylsonf.classgram.presentation.ui.utils.launchAndRepeatWithViewLifecycle
import cv.edylsonf.classgram.presentation.widget.FadingSnackbar
import kotlinx.coroutines.flow.collect

/**
 * An extension for Fragments that sets up a Snackbar with a [SnackbarMessageManager].
 */
fun Fragment.setupSnackbarManager(
    snackbarMessageManager: SnackbarMessageManager,
    fadingSnackbar: FadingSnackbar
) {
    launchAndRepeatWithViewLifecycle {
        snackbarMessageManager.currentSnackbar.collect { message ->
            if (message == null) { return@collect }
            val messageText = HtmlCompat.fromHtml(
                requireContext().getString(message.messageId),
                FROM_HTML_MODE_LEGACY
            )
            fadingSnackbar.show(
                messageText = messageText,
                actionId = message.actionId,
                longDuration = message.longDuration,
                actionClick = {
                    snackbarMessageManager.processDismissedMessage(message)
                    fadingSnackbar.dismiss()
                },
                // When the snackbar is dismissed, ping the snackbar message manager in case there
                // are pending messages.
                dismissListener = {
                    snackbarMessageManager.removeMessageAndLoadNext(message)
                }
            )
        }
    }
}