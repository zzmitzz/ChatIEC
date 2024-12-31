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
    onPassLogin: () -> Unit
) {
    val loginNavCtr = rememberNavController()
    val viewModel by viewModels<LoginViewModel>()
    NavHost(
        navController = loginNavCtr,
        startDestination = "Login"
    ) {
        composable(LoginRouteLink.Login.route) {
            LoginScreen() { }
        }
        composable(LoginRouteLink.Register.route) {

        }
        composable(LoginRouteLink.ForgotPassword.route) {

        }
    }
}