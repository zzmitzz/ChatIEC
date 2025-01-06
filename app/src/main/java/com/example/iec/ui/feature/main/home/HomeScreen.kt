package com.example.iec.ui.feature.main.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.iec.R


@Composable
fun HomeScreenStateful(
    viewModel: ViewModel,
    navController: NavController
) {
    var screenType by remember { mutableStateOf(ProfileType.CHECK) }
    HomeScreen(
        screenType = screenType,
        onScreenTypeChange = {
            Log.d("HomeScreen", "onScreenTypeChange called") // Add this
            screenType = if (screenType == ProfileType.PROFILE) {
                Log.d("HomeScreen", "Changing to CHECK") // Add this
                ProfileType.CHECK
            } else {
                Log.d("HomeScreen", "Changing to PROFILE") // Add this
                ProfileType.PROFILE
            }
        }
    )
}


@Composable
fun HomeScreen(
    screenType: ProfileType = ProfileType.CHECK,
    onScreenTypeChange: () -> Unit = {}
) {
    LaunchedEffect(screenType) {
        Log.d("HomeScreen", "Screen type changed to: $screenType")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color(0xFFEDEEF3)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Dashboard",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )

                Row {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.scanner),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (screenType == ProfileType.PROFILE) Color.White else Color.Transparent)
                    .padding(8.dp)
                    .clickable(
                        onClick = { onScreenTypeChange.invoke() }
                    )
            ) {
                Text(
                    text = "Profile",
                    color = if (screenType == ProfileType.PROFILE) Color.Blue else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "•",
                fontSize = 23.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (screenType == ProfileType.CHECK) Color.White else Color.Transparent)
                    .padding(8.dp)
                    .clickable(
                        onClick = {
                            onScreenTypeChange.invoke()
                        }
                    )
            ) {
                Text(
                    text = "Check-in",
                    color = if (screenType == ProfileType.CHECK) Color.Blue else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(30.dp)
            ) {
                ProfileComponent(
                    800.dp,
                    screenType = screenType
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}