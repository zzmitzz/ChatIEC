package com.example.iec.ui.feature.main.home.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CheckInStateful() {
    CheckInScreen(
        onGetLocationClick = {}
    )
}


@Composable
fun CheckInScreen(
    onGetLocationClick: () -> Unit,
    userName: String = "Ngô Tuấn Anh",
) {
    val randomLat = remember { (10..20).random() + Math.random() }
    val randomLong = remember { (105..110).random() + Math.random() }
    val expectedCheckInTime = remember {
        val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        val randomDate = Date(System.currentTimeMillis() + (1..24).random() * 3600000)
        formatter.format(randomDate)
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // User Name
            Text(
                text = "User: $userName",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Blue)
                    .clickable {
                        onGetLocationClick.invoke()
                    }
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        tint = Color.White,
                        contentDescription = "Get Location"
                    )
                    Text(
                        text = "Location",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Location Information Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Event Location
                Text(
                    text = "Địa điểm sự kiện:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = String.format("%.6f, %.6f", randomLat, randomLong),
                    color = Color.Gray
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Check-in Location
                Text(
                    text = "Địa điểm check-in:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = String.format("%.6f, %.6f", randomLat + 0.001, randomLong + 0.001),
                    color = Color.Gray
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Expected Check-in Time
                Text(
                    text = "Thời gian dự kiến check-in:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = expectedCheckInTime,
                    color = Color.Gray
                )
            }
        }

        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {},
        ) {
            Text(
                text = "Check-in",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CheckInPreview() {
    CheckInScreen(
        userName = "Ngô Tuấn Anh",
        onGetLocationClick = {}
    )
}