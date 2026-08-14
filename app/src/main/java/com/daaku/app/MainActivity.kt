package com.daaku.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DaakuBackground = Color(0xFF050811)
private val DaakuHeader = Color(0xFF080E19)
private val DaakuCard = Color(0xFF0D1422)
private val DaakuAccent = Color(0xFF00E5FF)
private val DaakuText = Color(0xFFEAFBFF)
private val DaakuSecondary = Color(0xFF8097AA)
private val MyMessage = Color(0xFF063B46)
private val OtherMessage = Color(0xFF151D2A)

data class Chat(
    val name: String,
    val message: String,
    val time: String
)

data class Message(
    val text: String,
    val isMine: Boolean,
    val time: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            DAAKUApp()
        }
    }
}

@Composable
fun DAAKUApp() {

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    var selectedChat by remember {
        mutableStateOf<Chat?>(null)
    }

    val chats = remember {
        listOf(
            Chat(
                name = "Aarav",
                message = "Welcome to DAAKU.",
                time = "12:41"
            ),
            Chat(
                name = "Priya",
                message = "Your private space is ready.",
                time = "11:20"
            ),
            Chat(
                name = "DAAKU AI",
                message = "How can I help you?",
                time = "10:05"
            )
        )
    }

    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme()
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DaakuBackground
        ) {

            if (selectedChat != null) {

                ChatScreen(
                    chat = selectedChat!!,
                    onBack = {
                        selectedChat = null
                    }
                )

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) {

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        when (selectedTab) {

                            0 -> ChatsScreen(
                                chats = chats,
                                onChatClick = {
                                    selectedChat = it
                                }
                            )

                            1 -> ContactsScreen()

                            2 -> ProfileScreen()

                            3 -> SettingsScreen()
                        }
                    }

                    NavigationBar(
                        containerColor = DaakuHeader
                    ) {

                        val navigationItems = listOf(
                            "Chats",
                            "Contacts",
                            "Profile",
                            "Settings"
                        )

                        navigationItems.forEachIndexed { index, title ->

                            NavigationBarItem(

                                selected = selectedTab == index,

                                onClick = {
                                    selectedTab = index
                                },

                                icon = {

                                    Text(
                                        text = when (index) {
                                            0 -> "●"
                                            1 -> "◎"
                                            2 -> "◉"
                                            else -> "⚙"
                                        },
                                        color =
                                            if (selectedTab == index)
                                                DaakuAccent
                                            else
                                                DaakuSecondary
                                    )
                                },

                                label = {
                                    Text(
                                        text = title,
                                        fontSize = 10.sp
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatsScreen(
    chats: List<Chat>,
    onChatClick: (Chat) -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredChats = chats.filter {
        it.name.contains(
            searchText,
            ignoreCase = true
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "DAAKU",
                color = DaakuAccent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Private Chat",
                color = DaakuText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(

                value = searchText,

                onValueChange = {
                    searchText = it
                },

                modifier = Modifier.fillMaxWidth(),

                singleLine = true,

                placeholder = {
                    Text(
                        text = "Search conversations",
                        color = DaakuSecondary
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DaakuAccent,
                    unfocusedBorderColor = Color(0xFF263448),
                    focusedTextColor = DaakuText,
                    unfocusedTextColor = DaakuText,
                    cursorColor = DaakuAccent
                ),

                shape = RoundedCornerShape(18.dp)
            )
        }

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp
            ),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(filteredChats) { chat ->

                ChatItem(
                    chat = chat,
                    onClick = {
                        onChatClick(chat)
                    }
                )
            }
        }
    }
}

@Composable
fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                DaakuCard,
                RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(14.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(

            modifier = Modifier
                .size(52.dp)
                .background(
                    DaakuAccent.copy(alpha = 0.12f),
                    CircleShape
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = chat.name.take(1),
                color = DaakuAccent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = chat.name,
                color = DaakuText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = chat.message,
                color = DaakuSecondary,
                fontSize = 13.sp,
                maxLines = 1
            )
        }

        Text(
            text = chat.time,
            color = DaakuSecondary,
            fontSize = 11.sp
        )
    }
}

@Composable
fun ChatScreen(
    chat: Chat,
    onBack: () -> Unit
) {

    val messages = remember {

        mutableStateListOf(

            Message(
                text = "Welcome to DAAKU.",
                isMine = false,
                time = "12:40"
            ),

            Message(
                text = "The future of private communication.",
                isMine = false,
                time = "12:41"
            ),

            Message(
                text = "Hello! DAAKU looks futuristic.",
                isMine = true,
                time = "12:42"
            )
        )
    }

    var messageText by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            listState.animateScrollToItem(
                messages.lastIndex
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .background(DaakuHeader)
                .padding(
                    horizontal = 6.dp,
                    vertical = 8.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = DaakuText
                )
            }

            Box(

                modifier = Modifier
                    .size(44.dp)
                    .background(
                        DaakuAccent.copy(alpha = 0.12f),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = chat.name.take(1),
                    color = DaakuAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = chat.name,
                    color = DaakuText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Online",
                    color = DaakuAccent,
                    fontSize = 11.sp
                )
            }
        }

        LazyColumn(

            state = listState,

            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),

            contentPadding = PaddingValues(
                horizontal = 14.dp,
                vertical = 18.dp
            ),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(messages) { message ->

                MessageBubble(message)
            }
        }

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .background(DaakuHeader)
                .imePadding()
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(

                value = messageText,

                onValueChange = {
                    messageText = it
                },

                modifier = Modifier.weight(1f),

                placeholder = {
                    Text(
                        text = "Type a message...",
                        color = DaakuSecondary
                    )
                },

                singleLine = true,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DaakuAccent,
                    unfocusedBorderColor = Color(0xFF263448),
                    focusedTextColor = DaakuText,
                    unfocusedTextColor = DaakuText,
                    cursorColor = DaakuAccent
                ),

                shape = RoundedCornerShape(22.dp)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            IconButton(

                onClick = {

                    if (messageText.isNotBlank()) {

                        messages.add(
                            Message(
                                text = messageText.trim(),
                                isMine = true,
                                time = "Now"
                            )
                        )

                        messageText = ""
                    }
                },

                modifier = Modifier
                    .size(52.dp)
                    .background(
                        DaakuAccent,
                        CircleShape
                    )
            ) {

                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color(0xFF001014)
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message
) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement =
            if (message.isMine)
                Arrangement.End
            else
                Arrangement.Start
    ) {

        Column(

            modifier = Modifier
                .background(
                    if (message.isMine)
                        MyMessage
                    else
                        OtherMessage,
                    RoundedCornerShape(18.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                )
        ) {

            Text(
                text = message.text,
                color = DaakuText,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = message.time,
                color = DaakuSecondary,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
fun ContactsScreen() {

    EmptyScreen(
        title = "CONTACTS",
        subtitle = "Your contacts will appear here."
    )
}

@Composable
fun ProfileScreen() {

    EmptyScreen(
        title = "PROFILE",
        subtitle = "Your DAAKU identity and account."
    )
}

@Composable
fun SettingsScreen() {

    EmptyScreen(
        title = "PRIVACY & SETTINGS",
        subtitle = "Security, privacy and app controls."
    )
}

@Composable
fun EmptyScreen(
    title: String,
    subtitle: String
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = title,
            color = DaakuAccent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = subtitle,
            color = DaakuText,
            fontSize = 19.sp
        )
    }
}
