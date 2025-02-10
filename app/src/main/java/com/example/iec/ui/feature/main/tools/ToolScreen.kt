package com.example.iec.ui.feature.main.tools

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.camera_ml.CameraActivity
import com.example.iec.R
import com.example.iec.ui.feature.ShimmerText
import com.example.iec.ui.feature.dropShadow
import com.example.iec.ui.getActivity


val backgroundColor = Color(0xFF1D1D1D)


@Composable
fun ToolScreenStateful(
){
    val toolViewModel: ToolVM = hiltViewModel()
    val hostedActivity = LocalContext.current.getActivity()
    val intent = Intent(hostedActivity, CameraActivity::class.java)
    ToolScreen(
        onCameraActivity = {hostedActivity?.startActivity(intent)}
    )

}


@Composable
fun ToolScreen(
    onCameraActivity: () -> Unit = {}
) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor.copy(alpha = 0.95f))
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .dropShadow(
                        shape = RectangleShape,
                        color = Color.Black.copy(0.5f),
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blur = 32.dp,
                        spread = 32.dp
                    )
                    ,
                horizontalArrangement = Arrangement.Center
            ) {
                ShimmerText(
                    text = "Tools",
                    textStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 22.sp,
                        color = Color.LightGray
                    ),
                    shimmerColors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        Color.LightGray
                    ),

                    )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            // Maps Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        val intent = Intent(context, HostActivity::class.java)
                        intent.putExtra(
                            HostActivity.SCREEN_CHOOSE,
                            ToolsFragmentScreen.SCANNER.name
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.map),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Direction",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Timeline Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.calendar__1_),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Schedule",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Document Scanner Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        val intent = Intent(context, DocsScannerActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.scanner),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Scan Docs",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.reminder),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Reminder",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        onCameraActivity.invoke()
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.translate__1_),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Translate",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ptit_iec),
                        contentDescription = "Timeline Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                val intent = Intent(context, HostActivity::class.java)
                                intent.putExtra(
                                    HostActivity.SCREEN_CHOOSE,
                                    ToolsFragmentScreen.NOTE.name
                                )
                                context.startActivity(intent)
                            }
                    )
                    Text(
                        text = "Notes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }


        }

    }

}


@Preview(
    showBackground = true
)
@Composable
fun ToolScreenPreview() {
    ToolScreen()
}