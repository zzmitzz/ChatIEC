package com.example.iec.ui.main

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.example.iec.navigation.ScreenDestinationLevel
import kotlinx.coroutines.CoroutineScope

@Stable
class IECAppState(
    private val navController: NavHostController,
    val scope: CoroutineScope
){

    fun navigateToScreenDestinationLevel(screen: ScreenDestinationLevel) {
        when(screen){
            ScreenDestinationLevel.Message -> {
            }
        }
    }
}