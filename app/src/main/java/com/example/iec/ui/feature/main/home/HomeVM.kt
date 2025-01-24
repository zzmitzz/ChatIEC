package com.example.iec.ui.feature.main.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iec.ui.feature.main.home.common.Location
import com.example.iec.ui.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import qrcode.QRCode
import qrcode.color.Colors
import java.net.URL
import java.util.Queue
import javax.inject.Inject


enum class ProfileType {
    PROFILE, CHECK
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val onError: String? = null,
    val selectedHomeScreen: ProfileType = ProfileType.PROFILE,
    val userInfoShow: UserInfo? = null,
    val qrCodeReceive: Bitmap? = null,
    val onNotificationMessage: Queue<String>? = null,
    val checkedInStatus: Boolean = false,
)


@HiltViewModel
class HomeVM @Inject constructor() : ViewModel() {
    private var _uiState = MutableStateFlow<HomeUIState>(HomeUIState())
    val uiState = _uiState


    private val scopeException = viewModelScope + CoroutineExceptionHandler() { _, throwable ->
        Log.d("Error", throwable.message.toString())
    }

    private suspend fun generateQRCode(userKey: String): Bitmap {
        return withContext(Dispatchers.Default) {
            val qrCreator = QRCode.ofSquares()
                .withColor(Colors.DEEP_SKY_BLUE)
                .withSize(24)
                .build(userKey)
            val imageByteArray = qrCreator.renderToBytes()
            BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        }
    }
    fun changeScreenType(type: ProfileType) {
        Log.d("HomeVM", type.name.toString())
        _uiState.update { currentState ->
            if(currentState.selectedHomeScreen == type){
                currentState
            }else{
                currentState.copy(selectedHomeScreen = type)
            }
        }
    }
    init {
        scopeException.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            _uiState.update {
                it.copy(isLoading = false, qrCodeReceive = generateQRCode(""), userInfoShow = )
            }
        }
    }
}
