package com.example.iec.ui.feature.main.message

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.uidata.ChatPreview
import com.example.iec.core.network.IECSocketManager
import com.example.iec.data.repository.MessageRepository
import com.example.data.uidata.Message
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.Date
import javax.inject.Inject


data class ChatScreenState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val stringQuery: String? = null,
    val dataReady: List<ChatPreview> = listOf(),
)


data class MessageScreenState(
    val chats: SnapshotStateList<Message> = mutableStateListOf(),
    val modelIsGenerating: Boolean = false,
    val i: Int = 0
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
    private lateinit var model: GenerativeModel
    private val scope = viewModelScope + CoroutineExceptionHandler { _, error ->
    }

    init {
//        initState()
        setupGemini()
    }

    private fun setupGemini() {
        model = GenerativeModel(
            "gemini-1.5-flash",
            apiKey = "AIzaSyBPfXNUtrzoY8TGAnjjMBBP447Er7sDAeU"
        )
    }

//    fun joinMessageChannel(userID: String = "") {
//        iecSocketManager.establishConnection(userID)
//    }

    private fun geminiAsking(message: String) = flow<String> {
        var output: String = ""
        model.generateContentStream(message)
            .collect {
                output += it.text
                emit(output)
            }
    }

    fun sendMessage(content: String) {
        scope.launch {
            _uiMessage.update {
                it.copy(
                    chats = it.chats.plus(
                        Message(
                            isFromUser = true,
                            message = content,
                            timestamp = System.currentTimeMillis()
                        )
                    ).toMutableStateList()
                )
            }
            _uiMessage.update {
                it.copy(
                    modelIsGenerating = true,
                    chats = it.chats.plus(
                        Message(
                            isFromUser = false,
                            message = "",
                            timestamp = System.currentTimeMillis()
                        )
                    ).toMutableStateList()
                )
            }
        }
        var i = 0
        geminiAsking(
            content
        ).onEach { mess ->
            _uiMessage.update {
                it.copy(
                    chats = it.chats.apply {
                        Log.d("ChatMessageVM", mess)
                        last().message = mess
                    },
                    i = i++
                )
            }
        }
            .onCompletion {
                _uiMessage.update {
                    it.copy(
                        modelIsGenerating = false
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(scope)

    }

//    fun onUpdateMessage() {
//        iecSocketManager.getStateStream().onEach { message ->
//            _uiMessage.update {
//                it.copy(
//                    chats =
//                )
//            }.also {
//                Log.d("ChatMessageVM", _uiMessage.value.chats.joinToString { "" })
//            }
//        }.launchIn(viewModelScope)
//    }

//    private fun initState() {
//
//        onUpdateMessage()
//        viewModelScope.launch {
//            onLoading()
//            _uiState.update {
//                it.copy(
//                    dataReady = dataSource.getPreviewChat()
//                )
//            }
//            Log.d("ChatMessageVM", _uiState.value.dataReady.toString())
//            offLoading()
//        }
//        joinMessageChannel("")
//    }

    fun updateStringQuery(text: String) {
        _uiState.update {
            it.copy(stringQuery = text)
        }
    }


    private fun onLoading() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }

    private fun offLoading() {
        _uiState.update {
            it.copy(isLoading = false)
        }
    }
}