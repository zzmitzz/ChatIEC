package com.example.iec.ui.feature.authorise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iec.DataStoreHelper
import com.example.iec.PreferenceKeys
import com.example.iec.data.APIResult
import com.example.iec.data.remote.LoginResponse
import com.example.iec.data.remote.RegisterRequest
import com.example.iec.data.remote.RegisterResponse
import com.example.iec.data.repository.AuthRepository
import com.example.iec.ui.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject



enum class RegisterStatus(val code: Int, val message: String? = null) {
    SUCCESS(1),
    FAILURE(2, "Something went wrong"),
}


data class LoginUIState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val errorMessage: String? = null
)
data class RegisterUIState(
    val isLoading: Boolean = false,
    val registerStatus: RegisterStatus? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStore: DataStoreHelper
) : ViewModel() {
    val uiState = MutableStateFlow(
        LoginUIState()
    )
    var emailInput: String = ""
    var passwordInput: String = ""
    val registerUIState = MutableStateFlow(
        RegisterUIState()
    )


    fun doRegister(data: RegisterRequest) = viewModelScope.launch {

        uiState.update { it.copy(isLoading = true) }
        delay(2000L)

        uiState.update { it.copy(isLoading = false) }
    }



    fun doLogin(username: String, password: String) = viewModelScope.launch {

        uiState.update { it.copy(isLoading = true) }
        val status = repository.doLogin(
            username, password
        )
        when(status){
            is APIResult.Success -> {
                dataStore.apply {
                    clearData(PreferenceKeys.USER_NAME)
                    clearData(PreferenceKeys.USER_PASSWORD)
                    saveData(PreferenceKeys.USER_NAME, status.data.username)
                    saveData(PreferenceKeys.USER_PASSWORD, status.data.password)
                }
                uiState.update { it.copy(loginResponse = status.data, isLoading = false) }
            }
            is APIResult.Failure -> {
                uiState.update { it.copy(errorMessage = status.exception, isLoading = false) }
            }
        }
    }
}