package com.example.iec.ui.feature.message

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.R
import com.example.iec.ui.theme.ColorPrimary
import com.example.iec.ui.theme.colorOnPrimary


@Composable
fun ChattingComponent(
    messages: List<Message>
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
    ){
        LazyColumn(
            modifier = Modifier
                .imePadding()
                .fillMaxSize(),

            reverseLayout = true
        ) {

            itemsIndexed(messages) { index, chatMessage ->
                MessageBubble((index == 0) || (index > 0 && messages[index-1].isFromUser), chatMessage)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onMessageSent: (String) -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(bottom = 8.dp),
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
                onValueChange = onMessageChange,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
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
                        onMessageSent(messageText)
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

@Composable
fun MessageBubble(
    showAvatar: Boolean,
    message: Message) {
    var onShowTimeStamp by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            if(!message.isFromUser){
                Image(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .alpha(if (showAvatar) 1f else 0f)
                        .background(color = colorOnPrimary, shape = CircleShape)
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture",
                )
            }
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = if (message.isFromUser) 20.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 20.dp
                ),
                color = ColorPrimary
            ) {

                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable(
                            interactionSource,
                            indication = null
                        ) {
                            onShowTimeStamp = !onShowTimeStamp
                        }

                ) {

                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = message.message,
                        color = if (message.isFromUser)
                            MaterialTheme.colorScheme.onSecondary
                        else
                            MaterialTheme.colorScheme.onSecondary,

                        )
                    if (onShowTimeStamp) {
                        Text(
                            text = convertTimeStamp(System.currentTimeMillis() - message.timestamp),
                            fontSize = 12.sp,
                            color = if (message.isFromUser)
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }

        }

    }
}

enum class UserStatus {
    ONLINE, OFFLINE
}

@Composable
fun TopAppBarMessage(
    userStatus: UserStatus = UserStatus.OFFLINE,
    onBackPressed: () -> Unit,
    onCallPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .background(color = Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onBackPressed()
                },
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
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "John Doe",
                fontSize = 16.sp,
                color = colorOnPrimary,
                fontWeight = FontWeight.Bold
            )
            when(userStatus){
                UserStatus.ONLINE -> {
                    Text(
                        "Online",
                        fontSize = 12.sp,
                        color = Color.Green
                    )
                }
                UserStatus.OFFLINE -> {
                    Text(
                        "Offline",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
        }

        Icon(
            imageVector = Icons.Default.Call,
            tint = ColorPrimary,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp)
                .clickable {
                    onCallPressed()
                }
        )
    }
}



@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun PreviewUI() {
    MessageBubble(true, Message(isFromUser = false, message = "Helsdafsdjfioasjdoifjaoisasdfasdfiojasodijfosiddfjaoisdjfoajdsoifjdosalo", timestamp = 0))

}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun PreviewUI1() {
    TopAppBarMessage(userStatus = UserStatus.OFFLINE,
        onBackPressed = {},
        onCallPressed = {})

}
@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun PreviewUI3() {
    MessageInput(
        messageText = "",
        onMessageChange = {},
        onMessageSent = {}
    )
}
@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun PreviewUI2() {
    ChattingComponent(listOf(
        Message(isFromUser = true, message = "Hello", timestamp = 0),
        Message(isFromUser = false, message = "Hello", timestamp = 0),
        Message(isFromUser = true, message = "Hello", timestamp = 0),
        Message(isFromUser = false, message = "Hello", timestamp = 0),
    ))

}

