package com.example.iec.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.BottomBarNav
import com.example.iec.NavigationGraph
import com.example.iec.ScreenDestinationLevel

@Composable
fun IECApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ScreenDestinationLevel.Home.route


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
            NavigationGraph(navController = navController, startDestination = ScreenDestinationLevel.Home)
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