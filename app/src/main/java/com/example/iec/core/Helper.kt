package com.example.iec.core

import android.content.Context
import android.os.Handler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//@Composable
//fun doubleClickDismiss(action: () -> Unit){
//    val context = LocalContext.current
//    val doubleClickInterval = 500L
//    val currentTime = System.currentTimeMillis()
//    val handler = Handler(context.mainLooper)
//    if (currentTime - lastClickTime < doubleClickInterval) {
//        action()
//    } else {
//        lastClickTime = currentTime
//    }
//}