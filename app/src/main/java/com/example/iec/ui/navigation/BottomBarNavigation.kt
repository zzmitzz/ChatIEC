package com.example.iec.ui.navigation

import android.content.Context
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.iec.core.toFloat
import com.example.iec.ui.feature.Canvas2
import com.example.iec.ui.theme.colorOnPrimary

//import com.skydoves.cloudy.cloudy


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomBarNav(
    homeScreen: () -> Unit,
    toolsScreen: () -> Unit,
    messageScreen: () -> Unit,
    faceRecognitionScreen: () -> Unit,
    currentScreen: String
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val blurRadius = 25f
                        renderEffect = android.graphics.RenderEffect
                            .createBlurEffect(
                                blurRadius,
                                blurRadius,
                                Shader.TileMode.MIRROR
                            )
                            .asComposeRenderEffect()
                    }
                }
                .blur(radius = 10.dp)  // Add blur effect
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray.copy(alpha = 0.95f)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemBottomBar(
                screenMetaData = ScreenDestinationLevel.Home,
                currentScreen == ScreenDestinationLevel.Home.route,
                onItemSelected = homeScreen
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
                onItemSelected = toolsScreen
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
                onItemSelected = messageScreen
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
                onItemSelected = faceRecognitionScreen
            )
        }
    }
}


@Composable
fun ItemBottomBar(
    screenMetaData: ScreenDestinationLevel,
    isSelected: Boolean = false,
    onItemSelected: () -> Unit,
    context: Context = LocalContext.current
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                onClick = {
                    onItemSelected()
                },
                indication = null,
                interactionSource = interactionSource
            )
            .background(color = Color.Transparent),
    ) {
        if(isSelected){
            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.5f),
                                Color.Transparent
                            ),
                        )
                    )
                    .align(Alignment.BottomCenter)
            ){

            }
        }
        Column(
            modifier = Modifier
                .height(52.dp)
                .width(64.dp)
                .padding(8.dp)
                .background(Color.Transparent)
        ) {

            if (isSelected) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    painter = painterResource(screenMetaData.unSelectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                    fontSize = 10.sp,
                    text = stringResource(screenMetaData.titleTextId),
                )
            } else {

                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    painter = painterResource(screenMetaData.selectedIcon),
                    contentDescription = stringResource(screenMetaData.iconText)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                    fontSize = 10.sp,
                    text = stringResource(screenMetaData.titleTextId),
                )


            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun BottomView() {
    BottomBarNav({}, {}, {}, {}, "")
}