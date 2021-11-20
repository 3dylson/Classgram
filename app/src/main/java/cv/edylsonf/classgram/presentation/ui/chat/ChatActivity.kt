package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cv.edylsonf.classgram.R
import cv.edylsonf.classgram.domain.models.Chat
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import org.w3c.dom.Text

class ChatActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            ClassgramTheme {
                // auth.currentUser?.let { ChatCard(Chat(null,null,null), it) }
                ChatCard()
            }
        }
    }
}


@Composable
private fun ChatCard() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
        .padding(all = 16.dp)
        .background(MaterialTheme.colors.surface)
    ) {
        Image(
            painter = painterResource(id = R.drawable.classgram_logo),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(8.dp),
            /*verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally*/
        ) {
            Text("Edylson Frederico")
            Row {
                Text(
                    "Yo classgram!",
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "-",
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "4h",
                    color = Color.DarkGray
                )
            }
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



@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ClassgramTheme {
        ChatCard()
    }
}