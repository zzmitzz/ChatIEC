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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.ui.navigation.BottomBarNav
import com.example.iec.ui.navigation.NavigationGraph
import com.example.iec.ui.navigation.ScreenDestinationLevel


@Composable
fun IECApp(
    iecAppState: IECAppState,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: DestinationRoute.Login.route

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (content, bottomBar) = createRefs()
        Box(modifier = Modifier
            .height(0.dp)
            .constrainAs(content){
                top.linkTo(parent.top)
                bottom.linkTo(if(currentRoute != DestinationRoute.Login.route) bottomBar.top else parent.bottom)
                height = Dimension.fillToConstraints
        }) {
            NavigationGraph(
                navController = navController,
                iecAppState = iecAppState
            )
        }

        if (currentRoute != DestinationRoute.Login.route) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomBar){
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }.wrapContentHeight()
            ){
                BottomBarNav(
                    homeScreen = {iecAppState.navToHome("Ngo Anh")},
                    toolsScreen = {iecAppState.navigateToTools()},
                    messageScreen = {iecAppState.navigateToMessage()},
                    faceRecognitionScreen = {iecAppState.navigateToFaceRecognition()},
                    currentScreen = currentRoute
                )
            }
        }
    }


}