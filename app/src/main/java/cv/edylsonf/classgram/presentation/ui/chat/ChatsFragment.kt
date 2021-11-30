package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.google.accompanist.insets.statusBarsPadding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import cv.edylsonf.classgram.presentation.ui.utils.BaseFragment
import cv.edylsonf.classgram.presentation.ui.utils.GlideHelper

class ChatsFragment: BaseFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var glideHelper: GlideHelper

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this).start()

        auth = Firebase.auth
        glideHelper = GlideHelper(requireContext())
        
        setContent {
            CompositionLocalProvider(LocalWindowInsets provides windowInsets ) {
                ClassgramTheme {
                    ChatScreen()
                }
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    private fun ChatScreen() {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = {
                        Text("Chats")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { requireActivity().onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            },
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier.statusBarsPadding()
        ) { innerPadding ->
            ChatList(Modifier.padding(innerPadding))
        }
    }

    @Composable
    private fun ChatList(modifier: Modifier = Modifier) {
        // We save the scrolling position with this state that can also
        // be used to programmatically scroll the list
        val scrollState = rememberLazyListState()
        // We save the coroutine scope where our animated scroll will be executed
        val coroutineScope = rememberCoroutineScope()

        // LazyColumn in Jetpack Compose is the equivalent of RecyclerView in Android Views.
        LazyColumn(state = scrollState) {
            items(20) {
                ChatCard("User #$it", modifier)
            }
        }
    }

    @Composable
    fun GlideImage(url: String, modifier: Modifier) {
        val image = glideHelper.fetchImage(url = url)

        if (image == null) {
            Image(
                painter = ColorPainter(Color.DarkGray),
                contentDescription = null,
                modifier = modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }

        else {
            Image(
                painter = image,
                contentDescription = null,
                modifier = modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }



    }


    @ExperimentalCoilApi
    @Composable
    private fun ChatCard(testString: String, modifier: Modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { findNavController().navigate(R.id.action_chatsFragment_to_conversationFragment) }
            )
            .padding(all = 8.dp)
            .background(MaterialTheme.colorScheme.surface)
        ) {
            /*Image(
                *//*painter = painterResource(id = R.drawable.classgram_logo),*//*
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )*/
            GlideImage(url = "https://developer.android.com/images/brand/Android_Robot.png", modifier = modifier)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(testString)
                Row {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            "Yo classgram!",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "-",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            "4h",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Outlined.PhotoCamera, contentDescription = null)
            }
        }
    }


    @ExperimentalMaterial3Api
    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun ChatScreenPreview() {
        ClassgramTheme {
            ChatScreen()
        }
    }
}