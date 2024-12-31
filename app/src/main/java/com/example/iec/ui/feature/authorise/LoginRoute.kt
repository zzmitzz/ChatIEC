package com.example.iec.ui.feature.authorise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope


@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
) {
    val scope = rememberCoroutineScope()
    LoginScreen(
        doLogin = viewModel::doLogin,
        doOtherLogin = {},
        onExitApp = {

        }
    )
}

fun onExitApp(){
    val timeInterval = 2000L
    val currentTime = System.currentTimeMillis()

}