package com.example.iec.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController


@Composable
fun BottomBarNav(navController: NavController){

    Row {
        ItemBottomBar(screenMetaData = ScreenDestinationLevel.YourTask)
        ItemBottomBar(screenMetaData = ScreenDestinationLevel.Message)
        ItemBottomBar(screenMetaData = ScreenDestinationLevel.CheckIn)
        ItemBottomBar(screenMetaData = ScreenDestinationLevel.Setting)
    }
}

@Composable
fun ItemBottomBar(screenMetaData: ScreenDestinationLevel, isSelected: Boolean = false){
    Box(
        modifier = Modifier.wrapContentSize()
    ){
        Column {
            if(isSelected){
                Image(
                    imageVector = screenMetaData.selectedIcon,
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    color = Color.Blue,
                    text = stringResource(screenMetaData.titleTextId),
                )
            }else{
                Image(
                    imageVector = screenMetaData.unSelectedIcon,
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    color = Color.Black,
                    text = stringResource(screenMetaData.titleTextId),
                )
            }

        }
    }
}