package com.example.iec.ui.feature.message

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.ui.theme.ColorPrimary
import com.example.iec.ui.theme.DarkGrayBg
import com.example.iec.ui.theme.IecTheme
import com.example.iec.ui.theme.PurpleGrey40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernChatScreen() {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }
    val onlineStatus by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayBg)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ){
            TopAppBarMessage(
                userStatus = if (onlineStatus) UserStatus.ONLINE else UserStatus.OFFLINE,
                onBackPressed = {},
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
    IecTheme {
        ModernChatScreen()
    }
}