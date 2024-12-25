package com.example.iec.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.iec.R
import kotlin.reflect.KClass

enum class TopScreenRoute(
    val route:String,
){
    Home("home-screen"),
    Translate("translate-screen"),
    Message("message-screen"),
    FaceRecognise("face-screen")

}
enum class ScreenDestinationLevel(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    Home(
        selectedIcon =  R.drawable.home,
        unSelectedIcon = R.drawable.home_filled,
        iconText = R.string.home,
        titleTextId = R.string.home,
        route = TopScreenRoute.Home.route
    ),
    Translate(
        selectedIcon = R.drawable.translate,
        unSelectedIcon = R.drawable.translation,
        iconText = R.string.translate,
        titleTextId = R.string.translate,
        route = TopScreenRoute.Translate.route
    ),
    Message(
        selectedIcon = R.drawable.chat,
        unSelectedIcon = R.drawable.chat_filled,
        iconText = R.string.chat,
        titleTextId = R.string.chat,
        route = TopScreenRoute.Message.route
    ),
    FaceRecognise(
        selectedIcon = R.drawable.face_filled,
        unSelectedIcon = R.drawable.face_filled,
        iconText = R.string.face_detect,
        titleTextId = R.string.face_detect,
        route = TopScreenRoute.FaceRecognise.route
    ),
}