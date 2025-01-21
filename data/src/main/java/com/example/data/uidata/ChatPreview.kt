package com.example.data.uidata

data class ChatPreview(
    val id: String,
    val senderName: String,
    val lastMessage: String,
    val timestamp: Long,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isVerified: Boolean = false,
    val avatarUrl: String? = null
)