package com.example.iec.ui.feature

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.iec.core.network.IECSocketManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


sealed class DestinationRoute(
    val route: String
) {

    // 0. Login
    data object Login : DestinationRoute("login"){
        fun createRoute() = "login"
    }

    data object Register: DestinationRoute("register"){
        fun createRoute() = "register"
    }

    // It should be split into lower level destinations
    // 1. Home
    data object Home : DestinationRoute("home") {
        fun createRoute() = "home"
    }


    // 2. Tools
    data object Tools : DestinationRoute("tools")

    // 3. Message
    data object Message : DestinationRoute("message")

    data object MessagePersonal : DestinationRoute("message/personal/{$AGR_MESS_ID}"){
        fun createRoute(messID: String) = "message/personal/$messID"
    }

    data object MessageDetail : DestinationRoute("message/detail/{$AGR_MESS_ID}")

    // 4. Face Recognition
    data object FaceRecognition : DestinationRoute("faceRecognition")

    companion object {
        const val AGR_HOME_USER_ID = "userID"
        const val AGR_MESS_ID = "messageID"

    }
}


@Singleton
class IECAppState @Inject constructor(
    private val navController: NavController,
    private val iecSocketManager: IECSocketManager
) {


    private var isOnline by mutableStateOf(checkIfOnline())


    private var _isShowBottomBar =  MutableStateFlow(false)
    val isShowBottomBar = _isShowBottomBar.asStateFlow()

    fun setBottomBarVisible(isVisible: Boolean) {
        _isShowBottomBar.value = isVisible
    }

    fun refreshOnlineStatus() {
        isOnline = checkIfOnline()
        iecSocketManager.establishConnection()
    }

    private fun checkIfOnline(): Boolean {
        // implement later
        return true
    }

    var webSocketState = iecSocketManager.establishConnection()
    // LOGIN

    fun navToLogin() {
        navController.navigate(DestinationRoute.Login.createRoute()) {
            launchSingleTop = true
            popUpTo(0){
                inclusive = true
            }
        }
    }

    fun navToRegister(){
        navController.navigate(DestinationRoute.Register.createRoute()) {
            launchSingleTop = true
            restoreState = true
        }
    }

    // 1. Home
    fun navToHome() {
        navController.navigate(DestinationRoute.Home.createRoute()) {
            launchSingleTop = true
            restoreState = true
        }
    }



    fun navigateToTools() {
        navController.navigate(DestinationRoute.Tools.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMessage() {
        navController.navigate(DestinationRoute.Message.route) {
            launchSingleTop = true
            restoreState = true
        }
    }


    fun navigateToMessagePersonal(messID: String) {
        navController.navigate(DestinationRoute.MessagePersonal.createRoute(messID)) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToFaceRecognition() {
        navController.navigate(DestinationRoute.FaceRecognition.route) {

            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateBack(){
        navController.popBackStack()
    }
}