package com.example.iec.ui.feature.main.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uidata.ChatPreview
import com.example.iec.core.network.IECSocketManager
import com.example.iec.data.repository.MessageRepository
import com.example.data.uidata.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



data class ChatScreenState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val stringQuery: String? = null,
    val dataReady: List<ChatPreview> = listOf(),
)

data class MessageScreenState(
    val chats: List<Message> = listOf(),
)

@HiltViewModel
class ChatMessageVM @Inject constructor(
    val dataSource: MessageRepository,
    val iecSocketManager: IECSocketManager,

) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChatScreenState()
    )
    private val _uiMessage = MutableStateFlow(MessageScreenState())
    val uiMessage = _uiMessage.asStateFlow()
    val uiState = _uiState.asStateFlow()


    init {
        initState()
    }

    fun joinMessageChannel(userID: String = ""){
        iecSocketManager.establishConnection(userID)
    }

    fun sendMessage(message: Message){
        viewModelScope.launch {
            iecSocketManager.sendMessage(message)
        }
    }

    fun onUpdateMessage(){
        iecSocketManager.getStateStream().onEach { message ->
            _uiMessage.update {
                it.copy(
                    chats = it.chats.plus(
                        Message(
                        isFromUser = true,
                        message = message,
                        timestamp = System.currentTimeMillis()
                    )
                    )
                )
            }.also {
                Log.d("ChatMessageVM", _uiMessage.value.chats.joinToString { "" })
            }
        }.launchIn(viewModelScope)
    }
    private fun initState(){

        onUpdateMessage()
        viewModelScope.launch {
            onLoading()
            _uiState.update {
                it.copy(
                    dataReady = dataSource.getPreviewChat()
                )
            }
            Log.d("ChatMessageVM", _uiState.value.dataReady.toString())
            offLoading()
        }
        joinMessageChannel("")
    }

    fun updateStringQuery(text: String){
        _uiState.update {
            it.copy(stringQuery = text)
        }
    }


    private fun onLoading(){
        _uiState.update {
            it.copy(isLoading = true)
        }
    }

    private fun offLoading(){
        _uiState.update {
            it.copy(isLoading = false)
        }
    }
}