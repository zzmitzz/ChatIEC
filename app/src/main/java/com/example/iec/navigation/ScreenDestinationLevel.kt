package com.example.iec.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.iec.R
import kotlin.reflect.KClass

enum class TopLevelRoute(
    val route:String,
){
    Message("MessageScreen")
}
enum class ScreenDestinationLevel(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    Message(
        selectedIcon = Icons.Rounded.Email,
        unSelectedIcon = Icons.Outlined.Email,
        iconText = R.string.message,
        titleTextId = R.string.message,
        route = TopLevelRoute.Message.route
    )
}