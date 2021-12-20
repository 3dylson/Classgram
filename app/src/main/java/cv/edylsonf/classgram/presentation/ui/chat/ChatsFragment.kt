package cv.edylsonf.classgram.presentation.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
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
        //TODO fix layout for screens with android native bottom nav

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

        var state by remember { mutableStateOf(0) }
        val icons = listOf( Icons.Filled.ChatBubble,  Icons.Filled.People)
        // Reuse the default offset animation modifier, but use our own indicator
        val indicator = @Composable { tabPositions: List<TabPosition> ->
            AnimatedIndicator(tabPositions = tabPositions, selectedTabIndex = state)
            //CustomIndicator(MaterialTheme.colorScheme.primary, Modifier.tabIndicatorOffset(tabPositions[state]))
        }

        Column {
            TabRow(selectedTabIndex = state, indicator = indicator, backgroundColor = Color.Transparent) {
                icons.forEachIndexed { index, icon ->
                    CustomTab(icon = icon, onClick = { state = index }, selected = (index == state) )
                    /*Tab(
                        icon = { Icon(icon, contentDescription = null) },
                        selected = state == index,
                        onClick = { state = index }
                    )*/
                }
            }
            if (state == 0) {
                // LazyColumn in Jetpack Compose is the equivalent of RecyclerView in Android Views.
                LazyColumn(state = scrollState) {
                    items(20) {
                        ChatCard("User #$it", modifier)
                    }
                }
            } else {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Group Chat tab ${state + 1} selected",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    @Composable
    private fun CustomTab(icon: ImageVector, onClick: () -> Unit, selected: Boolean) {
        Tab(selected = selected, onClick = onClick) {
            Column(
                Modifier.padding(10.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    if (selected) MaterialTheme.colorScheme.primary else Color.DarkGray
                )
            }
        }
    }


    @Composable
    private fun AnimatedIndicator(tabPositions: List<TabPosition>, selectedTabIndex: Int) {
        val transition = updateTransition(selectedTabIndex, label = "indicator transition")
        val indicatorStart by transition.animateDp(
            transitionSpec = {
                // Handle directionality here, if we are moving to the right, we
                // want the right side of the indicator to move faster, if we are
                // moving to the left, we want the left side to move faster.
                if (initialState < targetState) {
                    spring(dampingRatio = 1f, stiffness = 50f)
                } else {
                    spring(dampingRatio = 1f, stiffness = 1000f)
                }
            }, label = "start indicator"
        ) {
            tabPositions[it].left
        }

        val indicatorEnd by transition.animateDp(
            transitionSpec = {
                // Handle directionality here, if we are moving to the right, we
                // want the right side of the indicator to move faster, if we are
                // moving to the left, we want the left side to move faster.
                if (initialState < targetState) {
                    spring(dampingRatio = 1f, stiffness = 1000f)
                } else {
                    spring(dampingRatio = 1f, stiffness = 50f)
                }
            }, label = "end indicator"
        ) {
            tabPositions[it].right
        }

        /*val indicatorColor by transition.animateColor {
            colors[it % colors.size]
        }*/

        CustomIndicator(
            // Pass the current color to the indicator
            MaterialTheme.colorScheme.primary,
            modifier = Modifier
                // Fill up the entire TabRow, and place the indicator at the start
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomStart)
                // Apply an offset from the start to correctly position the indicator around the tab
                .offset(x = indicatorStart)
                // Make the width of the indicator follow the animated width as we move between tabs
                .width(indicatorEnd - indicatorStart)
        )
    }

    @Composable
    private fun CustomIndicator(color: Color, modifier: Modifier) {
        // Draws a rounded rectangular with border around the Tab, with a 5.dp padding from the edges
        // Color is passed in as a parameter [color]
        Box(
            modifier
                .padding(5.dp)
                .fillMaxSize()
                .border(BorderStroke(2.dp, color), RoundedCornerShape(5.dp))
        )
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