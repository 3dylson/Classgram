package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.firebase.auth.FirebaseAuth
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.databinding.ActivityChatBinding
import cv.edylsonf.classgram.presentation.ui.chat.conversation.LocalBackPressedDispatcher
import cv.edylsonf.classgram.presentation.ui.utils.GlideHelper

class ChatActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var glideHelper: GlideHelper

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*auth = Firebase.auth
        glideHelper = GlideHelper(applicationContext)*/

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Provide WindowInsets to our content. We don't want to consume them, so that
            // they keep being pass down the view hierarchy (since we're using fragments).
            ProvideWindowInsets(consumeWindowInsets = false) {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides this.onBackPressedDispatcher
                ) {
                    val scaffoldState = rememberScaffoldState()
                }
                    AndroidViewBinding(ActivityChatBinding::inflate)

            }
        }
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.chatsFragment)
        if (frag != null && frag.isVisible) super.onBackPressed()
        else onSupportNavigateUp()
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.chat_nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }


}


/*@Composable
private fun ChatCard(chat: Chat, currentUser: FirebaseUser) {
    var allParticipants: List<String>? = null
    var destUser: String = ""
    allParticipants = chat.participants
    allParticipants?.forEach { participant ->
        if (participant == currentUser.uid) {
            setDestUser(participant)
        }
    }

    //Text(text = chat.participants)
}*/




