package com.example.iec.ui.feature.main.home.common

import android.text.format.DateUtils
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale


data class Location(
    val lat: Double,
    val lng: Double
)


@Composable
fun CheckInStateful(
    onGetLocationClick: () -> Location?,
    onCheckInClick: () -> Unit
) {
    val context = LocalContext.current
    CheckInScreen(
        onGetLocationClick = onGetLocationClick,
        userName = "ZzMITzZ",
        onCheckInClick = {
            Toast.makeText(context, " ✅ Successful check-in", Toast.LENGTH_SHORT).show()
        }
    )
}


@Composable
fun CheckInScreen(
    onGetLocationClick: () -> Location?,
    onCheckInClick: () -> Unit,
    userName: String = "Ngô Tuấn Anh",
) {

    // Later fetch Location from Server.
    val eventLocation = Location(20.981173435734032, 105.78749159814292)
    var checkInLocation: Location? = null

    LaunchedEffect(Unit) {
        checkInLocation = onGetLocationClick.invoke()
    }

//    val expectedCheckInTime = remember {
//        val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
//        val randomDate = LocalDateTime.now()
//        formatter.format(randomDate)
//    }

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
                        if (checkInLocation == null) checkInLocation = onGetLocationClick.invoke()
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
                    text = "Học viện công nghệ Bưu Chính Viễn Thông",
                    color = Color.Gray
                )
                Text(
                    text = String.format(
                        "Lat: %.6f,  Lng: %.6f",
                        eventLocation.lat,
                        eventLocation.lng
                    ),
                    color = Color.Gray
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Check-in Location
                Text(
                    text = "Địa điểm check-in:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = checkInLocation?.let {
                        String.format(
                            "Lat: %.6f,  Lng: %.6f",
                            it.lat,
                            it.lng
                        )
                    } ?: "Can't get location",
                    color = Color.Gray
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Expected Check-in Time
                Text(
                    text = "Thời gian dự kiến check-in:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "expectedCheckInTime",
                    color = Color.Gray
                )
            }
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = if (checkInLocation != null) Color.Green else Color.Gray
                )
                .padding(8.dp)
                .clickable {
                    if(checkInLocation != null) onCheckInClick.invoke()
                },
        ) {
            Text(
                modifier = Modifier,
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
        onGetLocationClick = { Location(0.0, 0.0) },
        onCheckInClick = {}
    )
}