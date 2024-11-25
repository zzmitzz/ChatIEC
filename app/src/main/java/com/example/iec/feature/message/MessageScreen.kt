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
import com.example.iec.ui.theme.IecTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernChatScreen() {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        modifier = Modifier.wrapContentSize(),
                        contentDescription = "Back"
                    )
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            "J",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "John Doe",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Online",
                            fontSize = 12.sp,
                            color = Color.Green
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically).padding(end = 12.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Chat Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp,
                    vertical = 8.dp),
            reverseLayout = true
        ) {
            items(messages.asReversed()) { chatMessage ->
                ChatMessageItem(chatMessage)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Message Input
        Surface(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            tonalElevation = 2.dp
        ) {
            Box(){
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                )
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Type a message...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(24.dp)
                )

                FloatingActionButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages.add(
                                ChatMessage(
                                    message = messageText,
                                    isFromUser = true,
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                            messageText = ""
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
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