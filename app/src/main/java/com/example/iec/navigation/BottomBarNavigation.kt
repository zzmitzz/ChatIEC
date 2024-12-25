package com.example.iec.navigation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.iec.ui.theme.colorOnPrimary


@Composable
fun BottomBarNav(navController: NavController?, currentScreen: String) {
    val onItemSelected: (ScreenDestinationLevel) -> Unit = { screenChosen ->
        navController!!.navigate(screenChosen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorOnPrimary),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemBottomBar(
            screenMetaData = ScreenDestinationLevel.Home,
            currentScreen == ScreenDestinationLevel.Home.route,
            onItemSelected
        )
        ItemBottomBar(
            screenMetaData = ScreenDestinationLevel.Translate,
            currentScreen == ScreenDestinationLevel.Translate.route,
            onItemSelected
        )
        ItemBottomBar(
            screenMetaData = ScreenDestinationLevel.Message,
            currentScreen == ScreenDestinationLevel.Message.route,
            onItemSelected
        )
        ItemBottomBar(
            screenMetaData = ScreenDestinationLevel.FaceRecognise,
            currentScreen == ScreenDestinationLevel.FaceRecognise.route,
            onItemSelected
        )
    }
}


@Composable
fun ItemBottomBar(
    screenMetaData: ScreenDestinationLevel,
    isSelected: Boolean = false,
    onItemSelected: (ScreenDestinationLevel) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onItemSelected(screenMetaData)
            },
    ) {
        Column(
            modifier = Modifier
                .width(80.dp)
                .padding(8.dp)
                .background(Color.Transparent)
        ) {
            if (isSelected) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(30.dp),
                    painter = painterResource(screenMetaData.unSelectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Blue,
                    text = stringResource(screenMetaData.titleTextId),
                )
            } else {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(30.dp),
                    painter = painterResource(screenMetaData.selectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    text = stringResource(screenMetaData.titleTextId),
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomView() {
    BottomBarNav(navController = null, currentScreen = "")
}