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
import com.example.iec.state.ApplicationStateHolder
import com.example.iec.ui.navigation.BottomBarNav
import com.example.iec.ui.navigation.NavigationGraph
import com.example.iec.ui.navigation.ScreenDestinationLevel

@Composable
fun IECApp(
    applicationStateHolder: ApplicationStateHolder
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ScreenDestinationLevel.Home.route
    val loadingState = applicationStateHolder.loadingStateHolder.loadingState.collectAsState()




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

    if(loadingState.value.isLoading){
        LoadingDialog()
    }

}