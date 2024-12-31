package com.example.iec

import androidx.annotation.StringRes

enum class TopScreenRoute(
    val route:String,
){
    Home("Home"),
    Translate("Translate"),
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

