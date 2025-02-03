package com.example.iec.ui.feature

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.core.network.ConnectionState
import com.example.iec.ui.navigation.BottomBarNav
import com.example.iec.ui.navigation.NavigationGraph
import com.example.iec.ui.navigation.ScreenDestinationLevel


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IECApp(
    iecAppState: IECAppState,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: DestinationRoute.Login.route
    val connectionState =
        iecAppState.webSocketState.collectAsStateWithLifecycle(ConnectionState.Idle)

    SideEffect {
        Log.d("IECApp", "onCreate entered ${connectionState.value}")
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (content, bottomBar) = createRefs()
        val isShowBottomBar = remember { mutableStateOf(true) }
        Box(modifier = Modifier
            .height(0.dp)
            .constrainAs(content) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(bottom = 16.dp)) {
            NavigationGraph(
                navController = navController,
                iecAppState = iecAppState,
                hideBottomBar = {
                    isShowBottomBar.value = false
                },
                showBottomBar = {
                    isShowBottomBar.value = true
                }
            )
        }

        if (isShowBottomBar.value) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomBar) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                BottomBarNav(
                    homeScreen = { iecAppState.navToHome() },
                    toolsScreen = { iecAppState.navigateToTools() },
                    messageScreen = { iecAppState.navigateToMessage() },
                    faceRecognitionScreen = { iecAppState.navigateToFaceRecognition() },
                    currentScreen = currentRoute
                )
            }
        }
    }

    if (connectionState.value is ConnectionState.Error) {
        CustomDialog(
            showDialog = true,
            onDismissRequest = { iecAppState.refreshOnlineStatus() },
            content = {
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = (connectionState.value as ConnectionState.Error).message
                    )
                }
            })
    }
}