package com.example.iec.data.repository

import com.example.data.uidata.ChatPreview
import com.example.iec.data.remote.MessageRemote
import javax.inject.Inject

interface MessageRepository {
    suspend fun getPreviewChat(): List<ChatPreview>
}

class MessageRepositoryImpl
    @Inject constructor(private val messageRemote: MessageRemote) : MessageRepository {
    override suspend fun getPreviewChat(): List<ChatPreview> {
        return messageRemote.getPreviewChat()
    }
}