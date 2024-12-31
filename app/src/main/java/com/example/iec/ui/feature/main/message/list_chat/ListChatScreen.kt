package com.example.iec.ui.feature.main.message.list_chat

import androidx.compose.runtime.Composable


sealed class ListChatState{
    data class Error(val message: String): ListChatState()
    data object Loading : ListChatState()
    data class AppState(val listUserOnline: List<String>) : ListChatState()
}

@Composable
fun ListChatScreen(){

}