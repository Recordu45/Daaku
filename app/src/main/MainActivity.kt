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
import androidx.compose.material.icons.filled.Add
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

data class Contact(
    val name: String,
    val username: String,
    val online: Boolean
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

    var showNewChat by remember {
        mutableStateOf(false)
    }

    val contacts = remember {
        listOf(
            Contact("Aarav", "@aarav", true),
            Contact("Priya", "@priya", true),
            Contact("Rahul", "@rahul", false),
            Contact("DAAKU AI", "@daakuai", true)
        )
    }

    val chats = remember {
        mutableStateListOf(
            Chat("Aarav", "Welcome to DAAKU.", "12:41"),
            Chat("Priya", "Your private space is ready.", "11:20"),
            Chat("DAAKU AI", "How can I help you?", "10:05")
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
                    onBack = { selectedChat = null }
                )
            } else if (showNewChat) {
                NewChatScreen(
                    contacts = contacts,
                    onBack = { showNewChat = false },
                    onContactClick = { contact ->
                        selectedChat = Chat(
                            contact.name,
                            "Start a private conversation.",
                            "Now"
                        )
                        showNewChat = false
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
                                onChatClick = { selectedChat = it },
                                onNewChat = { showNewChat = true }
                            )
                            1 -> ContactsScreen(
                                contacts = contacts,
                                onContactClick = { contact ->
                                    selectedChat = Chat(
                                        contact.name,
                                        "Start a private conversation.",
                                        "Now"
                                    )
                                }
                            )
                            2 -> ProfileScreen()
                            3 -> SettingsScreen()
                        }
                    }

                    NavigationBar(
                        containerColor = DaakuHeader
                    ) {
                        val navigationItems = listOf(
                            "Chats", "Contacts", "Profile", "Settings"
                        )

                        navigationItems.forEachIndexed { index, title ->
                            NavigationBarItem(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                icon = {
                                    Text(
                                        text = when (index) {
                                            0 -> "●"
                                            1 -> "◎"
                                            2 -> "◉"
                                            else -> "⚙"
                                        },
                                        color = if (selectedTab == index)
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
    onChatClick: (Chat) -> Unit,
    onNewChat: () -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }

    val filteredChats = chats.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 12.dp,
                    top = 20.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
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
            }

            IconButton(
                onClick = onNewChat,
                modifier = Modifier
                    .size(48.dp)
                    .background(DaakuAccent, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Chat",
                    tint = Color(0xFF001014)
                )
            }
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                ),
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
                    onClick = { onChatClick(chat) }
                )
            }
        }
    }
}

@Composable
fun NewChatScreen(
    contacts: List<Contact>,
    onBack: () -> Unit,
    onContactClick: (Contact) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }

    val filteredContacts = contacts.filter {
        it.name.contains(searchText, ignoreCase = true) ||
            it.username.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DaakuHeader)
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = DaakuText
                )
            }

            Text(
                text = "New Chat",
                color = DaakuText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search name or username",
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredContacts) { contact ->
                ContactItem(
                    contact = contact,
                    onClick = { onContactClick(contact) }
                )
            }
        }
    }
}

@Composable
fun ContactsScreen(
    contacts: List<Contact>,
    onContactClick: (Contact) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
        ) {
            Text(
                text = "DAAKU",
                color = DaakuAccent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Contacts",
                color = DaakuText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
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
            items(contacts) { contact ->
                ContactItem(
                    contact = contact,
                    onClick = { onContactClick(contact) }
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                DaakuCard,
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
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
                text = contact.name.take(1),
                color = DaakuAccent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = contact.name,
                color = DaakuText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = contact.username,
                color = DaakuSecondary,
                fontSize = 13.sp
            )
        }

        Text(
            text = if (contact.online) "Online" else "Offline",
            color = if (contact.online)
                DaakuAccent
            else
                DaakuSecondary,
            fontSize = 11.sp
        )
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
            .clickable { onClick() }
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

        Spacer(modifier = Modifier.width(14.dp))

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
                "Welcome to DAAKU.",
                false,
                "12:40"
            ),
            Message(
                "The future of private communication.",
                false,
                "12:41"
            ),
            Message(
                "Hello! DAAKU looks futuristic.",
                true,
                "12:42"
            )
        )
    }

    var messageText by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
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
            IconButton(onClick = onBack) {
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

            Spacer(modifier = Modifier.width(12.dp))

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
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
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

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        messages.add(
                            Message(
                                messageText.trim(),
                                true,
                                "Now"
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

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = message.time,
                color = DaakuSecondary,
                fontSize = 9.sp
            )
        }
    }
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

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = subtitle,
            color = DaakuText,
            fontSize = 19.sp
        )
    }
}
