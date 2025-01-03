package com.example.iec.ui.feature.authorise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iec.ui.feature.main.message.box_chat_message.Message
import com.example.iec.ui.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject


data class LoginUIState(
    val isLoading: Boolean = false,
    val errorMessage: Queue<String>
)

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val repository: RepositoryImpl
) : ViewModel() {
    val uiState = MutableStateFlow(
        LoginUIState(
            false,
            LinkedList<String>()
        )
    )

    fun doLogin(email: String, password: String) = viewModelScope.launch {

        uiState.update { it.copy(isLoading = true) }

        // Simulate Login phase, simply return true for success Login, fail for failed Login.
        delay(2000)
        val userInfo = UserInfo(
            imageProfile = "NULL",
            name = "NULL",
            email = email,
            birth = "06/12/2003",
            currentJob = null,
            quotes = null,
            sessionToken = null
        )

        uiState.update { it.copy(isLoading =  false) }
    }
}