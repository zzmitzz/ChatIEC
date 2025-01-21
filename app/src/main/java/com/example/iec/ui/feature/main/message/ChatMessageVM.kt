package com.example.iec.ui.feature.main.message

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uidata.ChatPreview
import com.example.iec.data.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



data class ChatScreenState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val stringQuery: String? = null,
    val dataReady: List<ChatPreview> = listOf()
)


@HiltViewModel
class ChatMessageVM @Inject constructor(
    val dataSource: MessageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChatScreenState()
    )

    val uiState = _uiState.asStateFlow()


    init {
        initState()
    }


    private fun initState(){
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