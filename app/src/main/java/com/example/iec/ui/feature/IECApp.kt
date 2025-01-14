package com.example.iec.ui.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.ui.navigation.BottomBarNav
import com.example.iec.ui.navigation.NavigationGraph
import com.example.iec.ui.navigation.ScreenDestinationLevel




sealed class DestinationRoute(
    val route: String
){
    // It should be split into lower level destinations
    // 1. Home
    data object Home : DestinationRoute("home/{$AGR_HOME_USER_ID}") {
        fun createRoute(usrID: String) = "home/$usrID"
    }
    data object EditUserInfo: DestinationRoute("home/editUserInfo/{$AGR_HOME_USER_ID}"){
        fun createRoute(usrID: String) = "home/editUserInfo/$usrID"
    }
    // 2. Tools
    data object Tools : DestinationRoute("tools")

    // 3. Message
    data object Message : DestinationRoute("message")

    data object MessagePersonal : DestinationRoute("message/personal/{$AGR_MESS_ID}")

    data object MessageDetail: DestinationRoute("message/detail/{$AGR_MESS_ID}")

    // 4. Face Recognition
    data object FaceRecognition : DestinationRoute("faceRecognition")

    companion object {
        const val AGR_HOME_USER_ID = "userID"
        const val AGR_MESS_ID = "messageID"

    }
}




@Composable
fun IECApp(
    iecAppState: IECAppState
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: DestinationRoute.Home.route

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (content, bottomBar) = createRefs()
        Box(modifier = Modifier
            .height(0.dp)
            .constrainAs(content){
                top.linkTo(parent.top)
                bottom.linkTo(bottomBar.top)
                height = Dimension.fillToConstraints
        }) {
            NavigationGraph(navController = navController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar){
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
            }.wrapContentHeight()
        ){
            BottomBarNav(
                navController = navController,
                currentScreen = currentRoute
            )
        }
    }


}