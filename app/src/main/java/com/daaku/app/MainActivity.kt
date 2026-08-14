package com.daaku.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DaakuBackground = Color(0xFF050811)
private val DaakuCard = Color(0xFF0D1422)
private val DaakuAccent = Color(0xFF00E5FF)
private val DaakuText = Color(0xFFEAFBFF)
private val DaakuSecondary = Color(0xFF8097AA)

data class Chat(
    val name: String,
    val message: String,
    val time: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    val chats = listOf(
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

    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme()
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DaakuBackground
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    when (selectedTab) {

                        0 -> ChatsScreen(chats)

                        1 -> ContactsScreen()

                        2 -> ProfileScreen()

                        3 -> SettingsScreen()
                    }
                }

                NavigationBar(
                    containerColor = Color(0xFF080E19)
                ) {

                    val items = listOf(
                        "Chats",
                        "Contacts",
                        "Profile",
                        "Settings"
                    )

                    items.forEachIndexed { index, title ->

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

@Composable
fun ChatsScreen(
    chats: List<Chat>
) {

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

                value = "",

                onValueChange = {},

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

            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp
            ),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(chats) { chat ->

                ChatItem(chat)
            }
        }
    }
}

@Composable
fun ChatItem(
    chat: Chat
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                DaakuCard,
                RoundedCornerShape(18.dp)
            )
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
