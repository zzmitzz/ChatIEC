package com.example.iec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.iec.ui.theme.colorOnPrimary


@Composable
fun BottomBarNav(
    navController: NavController?,
    currentScreen: String
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
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 16.dp))
            .padding(vertical = 2.dp)
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
                modifier = Modifier
                    .height(8.dp)
                    .width(3.dp)
                    .background(Color.DarkGray)
            )
            ItemBottomBar(
                screenMetaData = ScreenDestinationLevel.Tools,
                currentScreen == ScreenDestinationLevel.Tools.route,
                onItemSelected
            )
            VerticalDivider(
                modifier = Modifier
                    .height(8.dp)
                    .width(3.dp)
                    .background(Color.DarkGray)
            )
            ItemBottomBar(
                screenMetaData = ScreenDestinationLevel.Message,
                currentScreen == ScreenDestinationLevel.Message.route,
                onItemSelected
            )
            VerticalDivider(
                modifier = Modifier
                    .height(8.dp)
                    .width(3.dp)
                    .background(Color.DarkGray)
            )
            ItemBottomBar(
                screenMetaData = ScreenDestinationLevel.FaceRecognise,
                currentScreen == ScreenDestinationLevel.FaceRecognise.route,
                onItemSelected
            )
        }
    }
}


@Composable
fun ItemBottomBar(
    screenMetaData: ScreenDestinationLevel,
    isSelected: Boolean = false,
    onItemSelected: (ScreenDestinationLevel) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                onClick = {onItemSelected(screenMetaData)},
                indication = null,
                interactionSource = interactionSource
            ),
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
    BottomBarNav(navController = null, currentScreen = "")
}