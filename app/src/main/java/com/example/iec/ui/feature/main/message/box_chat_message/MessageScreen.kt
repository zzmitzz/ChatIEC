package com.example.iec.ui.feature.main.message.box_chat_message

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.uidata.Message
import com.example.iec.ui.feature.main.message.ChatMessageVM
import com.example.iec.ui.theme.IECTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernChatScreen(
    userName: String = "",
    onBackPress: () -> Unit
) {
    val viewModel: ChatMessageVM = hiltViewModel()
    var messageText by remember { mutableStateOf("") }
    val uiState = viewModel.uiMessage.collectAsStateWithLifecycle()
    val onlineStatus by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
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
        ) {
            ChattingComponent(uiState.value.chats)
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            MessageInput(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onMessageSent = {

                    viewModel.sendMessage(
                        Message(
                            message = messageText,
                            isFromUser = true,
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
    IECTheme {
        ModernChatScreen("John Doe", {})
    }
}