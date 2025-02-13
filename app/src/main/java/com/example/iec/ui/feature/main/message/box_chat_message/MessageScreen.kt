package com.example.iec.ui.feature.main.message.box_chat_message

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.uidata.Message
import com.example.iec.ui.feature.main.message.ChatMessageVM
import com.example.iec.ui.feature.main.message.MessageScreenState
import com.example.iec.ui.theme.ColorPrimary
import com.example.iec.ui.theme.IECTheme


@Composable
fun ModernChatScreen(
    userName: String = "",
    onBackPress: () -> Unit
) {
    val viewModel: ChatMessageVM = hiltViewModel()
    var messageText by remember { mutableStateOf("") }
    val uiState = viewModel.uiMessage.collectAsState(
        initial = MessageScreenState()
    )
    val onlineStatus by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .background(color = ColorPrimary)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorPrimary)
                .padding(top = 12.dp)
        ) {
            TopAppBarMessage(
                userStatus = if (onlineStatus) UserStatus.ONLINE else UserStatus.OFFLINE,
                onBackPressed = onBackPress,
                onCallPressed = {}
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            ChattingComponent(
                uiState.value.chats,
                uiState.value.modelIsGenerating
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            MessageInput(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onMessageSent = {
                    if (!uiState.value.modelIsGenerating) {
                        viewModel.sendMessage(
                            messageText
                        )
                        messageText = ""
                    }
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