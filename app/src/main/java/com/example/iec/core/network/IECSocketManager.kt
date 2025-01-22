package com.example.iec.core.network

import android.util.Log
import com.example.iec.ui.feature.main.message.box_chat_message.Message
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


sealed class ConnectionState {
    data object Idle : ConnectionState()
    data class Connected(val isConnected: Boolean) : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}

@Singleton
class IECSocketManager @Inject constructor(
    val httpClient: HttpClient
){

    private var webSocket: WebSocketSession? = null

    private suspend fun connect(channelID: String){
        webSocket = httpClient.webSocketSession(SOCKET_URL_CHAT)
        Log.d("IECSocketManager", "connect with $webSocket ${this.hashCode()}")
    }
    fun establishConnection(route: String = "") = flow<ConnectionState> {
        emit(ConnectionState.Idle)
        try {
            withTimeout(3000){
                connect(route)
                Log.d("IECSocketManager", "Connected")
                emit(ConnectionState.Connected(true))
                Log.d("IECSocketManager", "Connect Successfully")
            }
        }catch (e: Exception){
            emit(ConnectionState.Error(message = "Connect error"))
        }
    }

    fun getStateStream(): Flow<String> {
        return flow<String> {
            val messageStates = webSocket
                ?.incoming
                ?.consumeAsFlow()
                ?.filterIsInstance<Frame.Text>()
                ?.mapNotNull {
                    it.readText()
                }
            emitAll(messageStates ?: emptyFlow())
        }
    }

    suspend fun sendMessage(message: Message){
        Log.d("IECSocketManager", this.hashCode().toString())
        Log.d("IECSocketManager", "send message ${message.message} && $webSocket")
        webSocket?.outgoing?.send(
            Frame.Text(Json.encodeToString(message))
        )
    }
    suspend fun disconnect(){
        webSocket?.close()
    }

    companion object {
        const val SOCKET_URL_CHAT = "wss://ws.ifelse.io"
    }
}