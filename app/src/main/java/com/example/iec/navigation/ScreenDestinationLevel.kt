package com.example.iec.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.iec.R
import kotlin.reflect.KClass

enum class TopScreenRoute(
    val route:String,
){
    YourTask("assign-screen"),
    Message("message-screen"),
    CheckIn("checkIn-screen"),
    Setting("setting-screen")

}
enum class ScreenDestinationLevel(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    YourTask(
        selectedIcon = Icons.Rounded.Build,
        unSelectedIcon = Icons.Outlined.Build,
        iconText = R.string.task,
        titleTextId = R.string.task,
        route = TopScreenRoute.YourTask.route
    ),
    Message(
        selectedIcon = Icons.Rounded.Email,
        unSelectedIcon = Icons.Outlined.Email,
        iconText = R.string.message,
        titleTextId = R.string.message,
        route = TopScreenRoute.Message.route
    ),
    CheckIn(
        selectedIcon = Icons.Rounded.LocationOn,
        unSelectedIcon = Icons.Outlined.LocationOn,
        iconText = R.string.message,
        titleTextId = R.string.message,
        route = TopScreenRoute.CheckIn.route
    ),
    Setting(
        selectedIcon = Icons.Rounded.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        iconText = R.string.message,
        titleTextId = R.string.message,
        route = TopScreenRoute.Setting.route
    ),
}