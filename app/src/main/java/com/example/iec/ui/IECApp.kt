package com.example.iec.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.BottomBarNav
import com.example.iec.NavigationGraph
import com.example.iec.ScreenDestinationLevel

@Composable
fun IECApp(
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ScreenDestinationLevel.Home.route


    var expandBottomBar by remember { mutableStateOf<Boolean>(false) }



    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (content, bottomBar) = createRefs()


        Box(modifier = Modifier) {
            NavigationGraph(navController = navController, startDestination = ScreenDestinationLevel.Home)
        }



        Box(
            modifier = Modifier.constrainAs(bottomBar){
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }.wrapContentSize()
        ){
            BottomBarNav(
                navController = navController,
                currentScreen = currentRoute ,
                isExpanded = expandBottomBar,
                onExpandClick = {
                    expandBottomBar = !expandBottomBar
                }
            )
        }
    }
}