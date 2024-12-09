package com.example.iec.ui.feature.message.list_chat

import androidx.compose.runtime.Composable


sealed class ListChatState{
    data object Loading : ListChatState()
    data class AppState(val listUserOnline: List<String>) : ListChatState()
}

@Composable
fun ListChatScreen(){

}