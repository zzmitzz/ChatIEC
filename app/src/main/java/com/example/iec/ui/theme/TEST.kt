package com.example.iec.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberDismissState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    fraction: Float,
    bottomSheetState: androidx.compose.material.BottomSheetScaffoldState
) {

    var count: Int by remember { mutableIntStateOf(0) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            count+=1
            true
        }
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val targetValue = bottomSheetState.bottomSheetState.targetValue
        val currentValue = bottomSheetState.bottomSheetState.currentValue
        val coroutineScope = rememberCoroutineScope() + CoroutineExceptionHandler{
            _, throwable ->
        }
        val color by animateColorAsState(
            targetValue = when(dismissState.targetValue) {
                DismissValue.Default -> Color.LightGray
                DismissValue.DismissedToEnd -> Color.Green
                DismissValue.DismissedToStart -> Color.Gray
            }, label = ""
        )
        val scale by animateFloatAsState(
            targetValue = if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f,
            label = ""
        )
        val icon = when(dismissState.dismissDirection) {
            DismissDirection.EndToStart -> Icons.Default.Check
            DismissDirection.StartToEnd -> Icons.Default.Close
            null -> Icons.Default.Check
        }
        Text("fraction = $fraction")
        Text("target = $targetValue")
        Text("current = $currentValue")
        Text("count = $count")

        Button(
            onClick = {
                coroutineScope.launch {
                    dismissState.reset()
                }
            }
        ) { Text(text = "Undo Dismiss") }
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier.padding(16.dp),
            dismissThresholds = {
                FractionalThreshold(0.5f)
            },
            background = {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .scale(scale)
                )
            },
            dismissContent = {
                Card {
                    Box(modifier = Modifier.size(100.dp).background(color)){
                        Icon(
                            imageVector = icon,
                            contentDescription = "Localized description",
                        )
                    }
                }
            },
            directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
        )
    }
}


@Preview
@Composable
private fun PreviewGradient() {
    RadialGradientBackground()

}