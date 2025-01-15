package com.example.iec.ui.feature.authorise

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


enum class LoginRouteLink(val route: String) {
    Login("login"),
    Register("register"),
    ForgotPassword("forgotPassword")
}

@Composable
fun LoginNavigation(
    navigateToRegister: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    val viewModel : LoginViewModel = viewModel()
    LoginRoute(viewModel, navigateToHome = navigateToHome)
}