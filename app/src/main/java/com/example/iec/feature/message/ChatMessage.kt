package com.example.iec.feature.message

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun MessageBubble(message: Message) {
    var onShowTimeStamp by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
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
                        text = message.message,
                        color = if (message.isFromUser)
                            MaterialTheme.colorScheme.onSecondary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,

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
            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(color = colorOnPrimary, shape = CircleShape)
                    .size(30.dp),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Picture",
            )
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
        modifier = Modifier.padding(horizontal = 8.dp).background(color = Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.wrapContentSize().clickable {
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
    MessageBubble(Message(isFromUser = true, message = "Hello", timestamp = 0))

}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun PreviewUI1() {
    TopAppBarMessage(userStatus = UserStatus.OFFLINE,
        onBackPressed = {},
        onCallPressed = {})

}