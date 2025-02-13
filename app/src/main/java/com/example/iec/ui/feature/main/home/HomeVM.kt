package com.example.iec.ui.feature.main.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camera.QRReaderML
import com.example.camera.QRResult
import com.example.iec.DataStoreHelper
import com.example.iec.PreferenceKeys
import com.example.iec.data.APIResult
import com.example.iec.data.repository.AuthRepository
import com.example.iec.ui.feature.main.home.common.Location
import com.example.iec.ui.model.UserInfo
import com.example.iec.ui.theme.ColorPrimary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    MY_WALLET, BIO, CHECK
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val onError: String? = null,
    val selectedHomeScreen: ProfileType = ProfileType.MY_WALLET,
    val userInfoShow: UserInfo? = null,
    val qrCodeGenerated: Bitmap? = null,
    val onNotificationMessage: Queue<String>? = null,
    val checkedInStatus: Boolean = false,
    var qrScannerResult: QRResult? = null
)


@HiltViewModel
class HomeVM @Inject constructor(
    private val authRemote: AuthRepository,
    private val dataStore: DataStoreHelper,
    private val qrCodeScanner: QRReaderML
) : ViewModel() {


    private var _uiState = MutableStateFlow<HomeUIState>(HomeUIState())
    val uiState = _uiState


    private var userInfo: UserInfo? = null
    private val scopeException = viewModelScope + CoroutineExceptionHandler() { _, throwable ->
        Log.d("Error", throwable.message.toString())
    }

    fun starScanQRCode() {
        qrCodeScanner.startScanCode().onEach { result ->
            _uiState.update {
                it.copy(
                    qrScannerResult = result
                )
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }


    private suspend fun generateQRCode(userKey: String): Bitmap {
        return withContext(Dispatchers.Default) {
            val qrCreator = QRCode.ofSquares()
                .withColor(Colors.DARK_GRAY)
                .withSize(24)
                .build(userKey)
            val imageByteArray = qrCreator.renderToBytes()
            BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        }
    }

    fun changeScreenType(type: ProfileType) {
        Log.d("HomeVM", type.name.toString())
        _uiState.update { currentState ->
            if (currentState.selectedHomeScreen == type) {
                currentState
            } else {
                currentState.copy(selectedHomeScreen = type)
            }
        }
    }

    private suspend fun getUser(username: String): UserInfo? {
        return when (val response = authRemote.getInfoUser(username)) {
            is APIResult.Success -> {
                response.data
            }
            else -> {
                null
            }
        }
    }


    fun updateUserInfo() = scopeException.launch {
        loading(true)
        val username = dataStore.readData(PreferenceKeys.USER_NAME).first() ?: "johndoe"
        userInfo = getUser(username)
        val qrCode = generateQRCode(username)
        if (userInfo == null) {
            _uiState.update {
                it.copy(onError = "User not found")
            }
        } else {
            _uiState.update {
                it.copy(userInfoShow = getUser(username), qrCodeGenerated = qrCode, isLoading = false)
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }
    init {

    }
}
