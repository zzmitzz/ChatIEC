package com.example.iec.feature.message

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMessageItem(message: ChatMessage) {
    var onShowTimeStamp by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (message.isFromUser) 20.dp else 4.dp,
                bottomEnd = if (message.isFromUser) 4.dp else 20.dp
            ),
            color = if (message.isFromUser)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
                modifier = Modifier.padding(12.dp).clickable(
                    interactionSource,
                    indication = null
                ) {
                    onShowTimeStamp = !onShowTimeStamp
                }
            ) {
                Text(
                    text = message.message,
                    color = if (message.isFromUser)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
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

fun convertTimeStamp(timeInMilli: Long): String {
    return when (val timeInSecond = timeInMilli / 1000) {
        in 0..59 -> "Send just now"
        in 60..3599 -> "${timeInSecond / 60} minutes ago"
        in 3600..86400 -> "${timeInSecond / 3600} hours ago"
        else -> {
            "Send farther before. "
        }
    }
}