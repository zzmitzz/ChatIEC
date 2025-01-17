package com.example.iec.ui.feature.main.tools

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.R
import com.example.iec.ui.getActivity


@Composable
fun ToolScreen(
    toolVM: ToolVM,

    ) {

}


@Composable
fun ToolScreen(

) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Tools",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
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
                        intent.putExtra(HostActivity.SCREEN_CHOOSE, ToolsFragmentScreen.SCANNER.name)
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
                        modifier = Modifier.size(48.dp)
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