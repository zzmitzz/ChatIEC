package com.example.iec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iec.navigation.BottomBarNav
import com.example.iec.navigation.NavigationGraph
import com.example.iec.navigation.ScreenDestinationLevel
import com.example.iec.ui.theme.IecTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IecTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    var expandBottomBar by remember { mutableStateOf<Boolean>(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBarNav(
                navController = navController,
                currentScreen = currentRoute ?: ScreenDestinationLevel.Home.route,
                isExpanded = expandBottomBar,
                onExpandClick = {
                    expandBottomBar = !expandBottomBar
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationGraph(navController = navController, startDestination = ScreenDestinationLevel.Home)
        }
    }
}