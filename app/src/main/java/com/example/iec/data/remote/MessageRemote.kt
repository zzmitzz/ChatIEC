package com.example.iec.data.remote

import com.example.data.uidata.ChatPreview
import retrofit2.http.GET

interface MessageRemote {
    @GET("466335de-1275-47a1-87b8-711d8e29300a")
    suspend fun getPreviewChat(): List<ChatPreview>
}