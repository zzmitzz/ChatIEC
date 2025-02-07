package com.example.iec.ui.feature.main.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.Pager
import com.example.camera.QRResult
import com.example.iec.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.roundToInt


data class FakeData(
    val name: String = "Ngo Tuan \n Anh",
    val live: String = "Ha Noi",
    val quotes: String = "Meme Meme"
)


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenStateless() {
    Scaffold(
        backgroundColor = Color(0xFF1C1C1C),
        topBar = {
            Row(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White,
                            modifier = Modifier.clickable {
//                            onMenuOpened = true
                            }
                        )
                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )
                        Text(
                            text = "Good, ...",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Row {
                        Icon(
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {}
//                                    onClick = {
//                                        qrScanner
//                                            .startScanCode()
//                                            .onEach {
//                                                when (it) {
//                                                    is QRResult.Success -> {
//                                                        qrContentResult = it.result
//                                                        Toast
//                                                            .makeText(
//                                                                context,
//                                                                "QR Code Scanned",
//                                                                Toast.LENGTH_SHORT
//                                                            )
//                                                            .show()
//                                                    }
//
//                                                    is QRResult.Canceled -> {
//                                                        Toast
//                                                            .makeText(
//                                                                context,
//                                                                "QR Code Scanning Cancelled",
//                                                                Toast.LENGTH_SHORT
//                                                            )
//                                                            .show()
//                                                    }
//
//                                                    is QRResult.Failed -> {
//                                                        Toast
//                                                            .makeText(
//                                                                context,
//                                                                "QR Code Scanning Failed",
//                                                                Toast.LENGTH_SHORT
//                                                            )
//                                                            .show()
//                                                    }
//                                                }
//                                            }
//                                            .launchIn(scope = scopeContext)
//                                    }
                                ),
                            painter = painterResource(R.drawable.sdf),
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            ComposablePickUp()
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 32.dp)
            ){
                Card(modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 16.dp, // Adjust the elevation as needed
                        shape = CardDefaults.shape, // Use the same shape as the card
                        clip = false, // Set to true if you want the shadow to be clipped to the shape
                        ambientColor = Color.White, // Ambient shadow color
                        spotColor = Color.White // Spot shadow color
                    )
                    ,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardColors(
                        containerColor = Color(0xFFD1D1D1),
                        contentColor = Color(0xFFD1D1D1),
                        disabledContainerColor = Color(0xFFD1D1D1),
                        disabledContentColor = Color(0xFFD1D1D1)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    MainComponents()
                }
            }

        }
    }
}


@Composable
fun MainComponents(){
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val progress = infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val offsetPx = with(LocalDensity.current) {
        300.dp.toPx()
    }
    Box(
        contentAlignment = Alignment.Center,
    ){
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset{
                    IntOffset((offsetPx*progress.value).roundToInt(), 0)
                }
                .background(Color.DarkGray, RoundedCornerShape(10.dp)),

        )
    }
}


@Composable
fun ComposablePickUp() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CardHolder("My Wallet", false)
        Text(
            text = "•",
            fontSize = 23.sp,
            color = Color.DarkGray
        )
        CardHolder("Bio", true)
        Text(
            text = "•",
            fontSize = 23.sp,
            color = Color.DarkGray
        )
        CardHolder("Contact", false)
    }
}

@Composable
fun CardHolder(type: String, isChosen: Boolean = false) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .shadow(
                elevation = 8.dp, // Adjust the elevation as needed
                shape = CardDefaults.shape, // Use the same shape as the card
                clip = false, // Set to true if you want the shadow to be clipped to the shape
                ambientColor = Color.White.copy(alpha = 0.95f), // Ambient shadow color
                spotColor = Color.White.copy(alpha = 0.95f) // Spot shadow color
            ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = Color(0xFFD1D1D1)
                )
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = type,
            fontFamily = FontFamily.SansSerif,
            fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal,
        )
    }
}


@Preview
@Composable
private fun HomePreview() {
    HomeScreenStateless()
}