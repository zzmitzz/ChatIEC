package com.example.iec.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iec.ui.feature.message.Message
import com.example.iec.ui.feature.message.ModernChatScreen


@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: ScreenDestinationLevel
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(route = ScreenDestinationLevel.YourTask.route) {
            ModernChatScreen()
        }
        composable(route = ScreenDestinationLevel.Message.route) {
            ModernChatScreen()
        }
        composable(route = ScreenDestinationLevel.CheckIn.route) {
            ModernChatScreen()
        }
        composable(route = ScreenDestinationLevel.Setting.route) {
            ModernChatScreen()
        }
    }
}