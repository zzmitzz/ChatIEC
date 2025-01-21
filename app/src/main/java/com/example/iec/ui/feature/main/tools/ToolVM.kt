package com.example.iec.ui.feature.main.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.IECNote
import com.example.iec.core.BaseViewModel
import com.example.iec.data.remote.NoteRemote
import com.example.iec.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



data class NoteFragmentState(
    val isLoading: Boolean = false,
    val notes: List<IECNote> = listOf()
)

@HiltViewModel
class ToolVM @Inject constructor(
    val noteRemote: NoteRepository
) : BaseViewModel() {
    var uiState: MutableStateFlow<NoteFragmentState> =  MutableStateFlow(NoteFragmentState())
    fun fakeTimeJob(){
        viewModelScope.launch {
            setLoading(true)
            delay(2000)
            setLoading(false)
        }
    }


    init {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(
                isLoading = true
            )
            getNoteV1()
        }
    }

    private suspend fun getNoteV1(): Unit{
        uiState.value = uiState.value.copy(
            isLoading = false,
            notes = noteRemote.getNoteOffline("123")
        )
    }
    private fun getNotes() = callbackFlow<NoteFragmentState> {
        trySend(
            uiState.value.copy(
                isLoading = true
            )
        )
        trySend(uiState.value.copy(
            isLoading = false,
            notes = noteRemote.getNoteOffline("123")
        ))
        awaitClose {
        }
    }
}