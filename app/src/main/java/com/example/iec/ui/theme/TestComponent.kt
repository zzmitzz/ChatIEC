package com.example.iec.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeHolder(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Good,...", fontSize = 18.sp) },
                backgroundColor = Color(0xFF272727),
                contentColor = Color(0xFF272727),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Refresh, contentDescription = "Sync")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavigationButton(text = "My Wallet")
                NavigationButton(text = "Bio")
                NavigationButton(text = "Contact")
            }
        }
    }
}


@Composable
fun NavigationButton(text: String) {
    Button(
        onClick = {},
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(text, fontWeight = FontWeight.Medium)
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    HomeHolder()
}