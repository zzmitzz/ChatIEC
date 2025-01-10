package com.example.iec.ui.feature.main.home

import androidx.lifecycle.ViewModel
import com.example.iec.ui.feature.main.home.common.Location
import com.example.iec.ui.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


enum class ProfileType {
    PROFILE, CHECK
}

sealed interface HomeUIState{
    data object Loading: HomeUIState
    data class ErrorMessage(val message: String? = null): HomeUIState
    data class HomeReady(
        val selectedHomeScreen: ProfileType = ProfileType.PROFILE,
        val onEditProfilePopUp: UserInfo? = null,
        val onQRCodeReady: Unit? = null

    )
}


@HiltViewModel
class HomeVM @Inject constructor(): ViewModel(){
    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    fun getLocation(): Location? {

    }
}