package com.example.iec.ui.navigation

import androidx.annotation.StringRes
import com.example.iec.R

enum class TopScreenRoute(
    val route:String,
){
    Home("Home"),
    Tools("Tools"),
    Message("Message"),
    FaceRecognise("FaceRecognise"),
    Login("Login"),
    Register("Register"),


}
enum class ScreenDestinationLevel(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    Home(
        selectedIcon = R.drawable.home,
        unSelectedIcon = R.drawable.home_filled,
        iconText = R.string.home,
        titleTextId = R.string.home,
        route = TopScreenRoute.Home.route
    ),
    Tools(
        selectedIcon = R.drawable.backpack_24dp_000000_fill0_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.backpack_24dp_000000_fill1_wght400_grad0_opsz24,
        iconText = R.string.tools,
        titleTextId = R.string.tools,
        route = TopScreenRoute.Tools.route
    ),
    Message(
        selectedIcon = R.drawable.chat,
        unSelectedIcon = R.drawable.chat_filled,
        iconText = R.string.chat,
        titleTextId = R.string.chat,
        route = TopScreenRoute.Message.route
    ),
    FaceRecognise(
        selectedIcon = R.drawable.ar_on_you_24dp_000000_fill0_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.ar_on_you_24dp_000000_fill1_wght400_grad0_opsz24,
        iconText = R.string.face_detect,
        titleTextId = R.string.face_detect,
        route = TopScreenRoute.FaceRecognise.route
    ),
}

