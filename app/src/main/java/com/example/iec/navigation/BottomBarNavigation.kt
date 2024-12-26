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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.example.iec.ui.theme.colorOnPrimary


@Composable
fun BottomBarNav(
    navController: NavController?,
    currentScreen: String,
    isExpanded: Boolean,
    onExpandClick: () -> Unit = {}
) {
    val onItemSelected: (ScreenDestinationLevel) -> Unit = { screenChosen ->
        navController!!.navigate(screenChosen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    if (isExpanded) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .wrapContentSize()
                .clip(RoundedCornerShape(topEnd = 16.dp))
                .background(colorOnPrimary)
                .padding(start = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 16.sp,
                    modifier = Modifier.rotate(90f),
                    text = "IEC",
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "",
                    Modifier
                        .size(15.dp)
                        .clickable {
                            onExpandClick()
                        }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .padding(end = 80.dp)
                .height(50.dp)
                .wrapContentSize()
                .clip(RoundedCornerShape(topEnd = 16.dp))
        ) {
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
                VerticalDivider(
                    modifier = Modifier.height(8.dp).width(3.dp).background(Color.DarkGray)
                )
                ItemBottomBar(
                    screenMetaData = ScreenDestinationLevel.Translate,
                    currentScreen == ScreenDestinationLevel.Translate.route,
                    onItemSelected
                )
                VerticalDivider(
                    modifier = Modifier.height(8.dp).width(3.dp).background(Color.DarkGray)
                )
                ItemBottomBar(
                    screenMetaData = ScreenDestinationLevel.Message,
                    currentScreen == ScreenDestinationLevel.Message.route,
                    onItemSelected
                )
                VerticalDivider(
                    modifier = Modifier.height(8.dp).width(3.dp).background(Color.DarkGray)
                )
                ItemBottomBar(
                    screenMetaData = ScreenDestinationLevel.FaceRecognise,
                    currentScreen == ScreenDestinationLevel.FaceRecognise.route,
                    onItemSelected
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    Modifier
                        .size(15.dp)
                        .clickable {
                            onExpandClick()
                        }
                )
            }
        }
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
            .clickable {
                onItemSelected(screenMetaData)
            },
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
                .background(Color.Transparent)
        ) {
            if (isSelected) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    painter = painterResource(screenMetaData.unSelectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Red,
                    fontSize = 12.sp,
                    text = stringResource(screenMetaData.titleTextId),
                )
            } else {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    painter = painterResource(screenMetaData.selectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 12.sp,
                    text = stringResource(screenMetaData.titleTextId),
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomView() {
    BottomBarNav(navController = null, currentScreen = "", isExpanded = false)
}