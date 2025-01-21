package com.example.data.uidata

data class MessageChatItem (
    val avatarUrl: String = "",
    val name: String = "Larry Machigo",
    val lastMessage: String = "Ok. Let me check",
    val timestamp: String = "09:38 AM",
    val isOnline: Boolean = false,
    val isTyping: Boolean = false,
)