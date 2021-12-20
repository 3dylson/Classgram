package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import cv.edylsonf.classgram.presentation.ui.chat.conversation.ConversationUiState
import cv.edylsonf.classgram.presentation.ui.chat.conversation.LocalBackPressedDispatcher
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment

class ConversationFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this)
            // We use the `windowInsetsAnimationsEnabled` parameter to enable animated
            // insets support. This allows our `ConversationContent` to animate with the
            // on-screen keyboard (IME) as it enters/exits the screen.
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher,
                LocalWindowInsets provides windowInsets,
            ) {
                ClassgramTheme {
                    ConversationContent(
                        uiState = ConversationUiState("Katia",2, initialMessages),
                        navigateToProfile = {},
                        // Add padding so that we are inset from any left/right navigation bars
                        // (usually shown when in landscape orientation)
                        //modifier = Modifier.navigationBarsPadding(bottom = false),
                    onBackClick = { requireActivity().onBackPressed() /*findNavController().navigate(R.id.chatsFragment) */})
                }
            }
        }
    }
}