package com.example.iec.ui.feature.message.list_chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBar(
    onMenuClick: () -> Unit,
    onAddNewClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Icon(
            modifier = Modifier.padding(end = 12.dp).clickable {
                onMenuClick()
            },
            imageVector = Icons.Default.Menu,
            contentDescription = "menu"
        )
        Text(
            text = "Chats",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "None",
            modifier = Modifier.clickable {
                onAddNewClick()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar({},{})
}