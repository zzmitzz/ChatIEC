package com.example.iec.ui.feature.authorise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.iec.ui.feature.LoadingDialog


@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    navigateToHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if(uiState.userInfo != null){
        navigateToHome()
    }
    LoginScreen(
        uiState = uiState,
        doLogin = viewModel::doLogin,
        doOtherLogin = {},
        onExitApp = {
        }
    )
    if(uiState.isLoading){
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            LoadingDialog()
        }
    }
}

fun onExitApp(){
    val timeInterval = 2000L
    val currentTime = System.currentTimeMillis()

}