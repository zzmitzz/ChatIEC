package com.example.iec.feature.message

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
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        ){
            TopAppBarMessage(
                userStatus = if (onlineStatus) UserStatus.ONLINE else UserStatus.OFFLINE,
                onBackPressed = {},
                onCallPressed = {}
            )
        }
        // Chat Messages
        LazyColumn(
            modifier = Modifier
                .imePadding()
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp,
                    vertical = 8.dp),
            reverseLayout = true
        ) {
            items(messages.asReversed()) { chatMessage ->
                MessageBubble(chatMessage)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Message Input
        Surface(
            modifier = Modifier.fillMaxWidth().background(color = Color.Black).padding(bottom = 8.dp),
            tonalElevation = 2.dp
        ) {

            Row(
                modifier = Modifier
                    .background(color = Color.Black)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.padding(end = 4.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = Color(0xFFA9A9A9),
                        contentDescription = "More options",
                    )
                }
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 4.dp),
                    placeholder = { Text("Type a message...", color = Color.White) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF454545),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),

                    shape = RoundedCornerShape(24.dp)
                )

                FloatingActionButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages.add(
                                Message(
                                    message = messageText,
                                    isFromUser = true,
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                            messageText = ""
                        }
                    },
                    containerColor = ColorPrimary,
                    modifier = Modifier.size(50.dp),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
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