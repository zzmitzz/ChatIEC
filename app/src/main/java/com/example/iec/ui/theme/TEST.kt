package com.example.iec.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RadialGradientBackground() {

    val modelBottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    var fraction = modelBottomSheetState.bottomSheetState.progress(
        BottomSheetValue.Collapsed,
        BottomSheetValue.Expanded
    )
    androidx.compose.material.BottomSheetScaffold(
        scaffoldState = modelBottomSheetState,
        sheetContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 1f - fraction
                        scaleY = 1f - fraction
                        scaleX = 1f - fraction
                    },
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "1",
                    fontSize = 24.sp
                )
                Text(
                    text = "2", fontSize = 24.sp

                )
                Text(
                    text = "3", fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,  // Center color
                                Color.Black   // Edge color
                            ),
                            // You can adjust the radius to control how far the gradient spreads
                            radius = 500f
                        )
                    )
            )
        }
    ) {
        MainContent(bottomSheetState = modelBottomSheetState, fraction = fraction)
    }
}


@Composable
fun MainContent(
    fraction: Float,
    bottomSheetState: androidx.compose.material.BottomSheetScaffoldState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val targetValue = bottomSheetState.bottomSheetState.targetValue
        val currentValue = bottomSheetState.bottomSheetState.currentValue

        Text("fraction = $fraction")
        Text("target = $targetValue")
        Text("current = $currentValue")
    }
}


@Preview
@Composable
private fun PreviewGradient() {
    RadialGradientBackground()

}