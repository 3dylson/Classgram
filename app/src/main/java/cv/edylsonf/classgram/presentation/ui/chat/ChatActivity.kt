package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme

class ChatActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            ClassgramTheme {
                // auth.currentUser?.let { ChatCard(Chat(null,null,null), it) }
                ChatScreen()
            }
        }
    }

    @Composable
    private fun ChatScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Chats")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
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
                ChatCard("User #$it")
            }
        }
    }


    @ExperimentalCoilApi
    @Composable
    private fun ChatCard(testString: String) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { /*TODO*/ }
            )
            .padding(all = 8.dp)
            .background(MaterialTheme.colors.surface)
        ) {
            Image(
                /*painter = painterResource(id = R.drawable.classgram_logo),*/
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
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
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "-",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            "4h",
                            style = MaterialTheme.typography.body2
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


    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun ChatScreenPreview() {
        ClassgramTheme {
            ChatScreen()
        }
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




