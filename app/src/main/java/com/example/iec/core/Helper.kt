package com.example.iec.core

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


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


fun Float.toDP(context: Context): Dp{
    val displayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  this, displayMetrics).dp
}


fun Dp.toFloat(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return this.value * (displayMetrics.densityDpi / 160f)
}


fun ComponentActivity.pixelToDp(px: Float): Float{
    val metric: DisplayMetrics = DisplayMetrics()
    val metrics = this.windowManager.defaultDisplay.getMetrics(metric)

    return px / (metric.densityDpi / 160f)
}