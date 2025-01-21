package com.example.iec.ui.feature.main.message.box_chat_message

import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iec.ui.theme.DarkGrayBg
import com.example.iec.ui.theme.IECTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernChatScreen(
    userName: String = "",
    onBackPress: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }
    val onlineStatus by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ){
            TopAppBarMessage(
                userStatus = if (onlineStatus) UserStatus.ONLINE else UserStatus.OFFLINE,
                onBackPressed = onBackPress,
                onCallPressed = {}
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            ChattingComponent(messages)
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            MessageInput(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onMessageSent = {
                    messages.add(0,
                        Message(
                            message = messageText,
                            isFromUser = messageText.startsWith("a"),
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    messageText = ""
                }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ModernChatScreenPreview() {
    IECTheme  {
        ModernChatScreen("John Doe"){}
    }
}