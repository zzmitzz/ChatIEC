package com.example.iec.ui.feature

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController






@Composable
fun rememberIECAppState(
    navController: NavController = rememberNavController(),
    context: Context
) = remember(navController, context){
    IECAppState(navController,context)
}




class IECAppState(
    private val navController: NavController,
    private val context: Context
){
    var isOnline by mutableStateOf(checkIfOnline())

    fun refreshOnlineStatus(){
        isOnline = checkIfOnline()
    }

    private fun checkIfOnline(): Boolean{
        // implement later
        return true
    }


}