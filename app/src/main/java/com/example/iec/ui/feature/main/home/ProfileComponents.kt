package com.example.iec.ui.feature.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.iec.R
import com.example.iec.ui.CustomDialog
import com.example.iec.ui.feature.main.home.common.CheckInStateful
import com.example.iec.ui.feature.main.home.common.EditProfileScreen
import com.example.iec.ui.feature.main.home.common.Location
import com.example.iec.ui.feature.main.home.common.RoundedAvatar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

enum class ProfileType {
    PROFILE, CHECK
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileComponent(
    screenType: ProfileType = ProfileType.PROFILE,
    onDiscovery: (Boolean) -> Unit = {},
    onSaveChange: () -> Unit = {},
    onShare: () -> Unit = {},
    viewModel: HomeVM
) {
    var discoveryMode by remember { mutableStateOf(false) }
    var onShowEditProfile by remember { mutableStateOf(false) }
    var actionShareProfile by remember { mutableStateOf(false) }
    var onCheckInDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var currentLocation: Location? by remember { mutableStateOf<Location?>(null) }


    // Request Permission
    val locationFinePermission =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val locationCoarsePermission =
        rememberPermissionState(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    LaunchedEffect(key1 = locationFinePermission, key2 = locationCoarsePermission) {
        if (!locationFinePermission.status.isGranted) {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!locationCoarsePermission.status.isGranted) {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
    var fusedLocationProviderClient by remember { mutableStateOf<FusedLocationProviderClient?>(null) }

    Log.d("ScreenType", screenType.toString())
    when (screenType) {
        ProfileType.PROFILE -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Profile Mode",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Hi, ZzZ",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Discovery",
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (discoveryMode) "ON" else "OFF",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = discoveryMode,
                            onCheckedChange = {
                                discoveryMode = it
                                onDiscovery(discoveryMode)
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = ""
                    )
                    RoundedAvatar(180)
                    Icon(
                        imageVector = Icons.Default.Edit,
                        modifier = Modifier.clickable(
                            onClick = {
                                onShowEditProfile = true
                            }
                        ),
                        contentDescription = ""
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = "John Smith :>",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 30.dp)) {
                    HorizontalDivider(color = Color.DarkGray)
                }

                Box(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    ProfileInformation()
                }
            }

        }

        ProfileType.CHECK -> {
            QRDisplayScreen("",
                onCheckIn = {
                    onCheckInDialog = true
                })
        }
    }


    CustomDialog(
        showDialog = onShowEditProfile,
        onDismissRequest = { onShowEditProfile = false },
    ) {
        EditProfileScreen {
            onSaveChange()
            onShowEditProfile = false
        }
    }

    CustomDialog(
        showDialog = onCheckInDialog,
        onDismissRequest = { onCheckInDialog = false },
    ) {
        CheckInStateful(
            onCheckInClick = {},
            currentLocation = currentLocation )
    }
}


@Composable
fun QRDisplayScreen(
    qrContent: String,
    onCheckIn: () -> Unit = {},
    onCheckOut: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // QR Code Display
        Card(
            modifier = Modifier
                .padding(32.dp)
                .weight(1f),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.qr),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(8.dp)
                )
            }
        }

        // Buttons Container
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Check In Button
            Button(
                onClick = onCheckIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50) // Green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "CHECK IN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Check Out Button
            Button(
                onClick = onCheckOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336) // Red
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "CHECK OUT",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun ProfileInformation(
    modifier: Modifier = Modifier,
    hometown: String = "PTIT, IEC",
    gender: String = "Male",
    age: Int = 21,
    jobTitle: String = "Research",
    company: String = "IEC",
    aboutMe: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sedLorem ipsum dolor sit amet, consectetur adipiscing elit, sed"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Personal Info Section
        InfoSection(
            title = "Hometown • Gender • Age",
            content = "$hometown • $gender • $age Years Old",
            titleColor = Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Job Info Section
        InfoSection(
            title = "Job Title • Company",
            content = "$jobTitle at $company",
            titleColor = Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // About Me Section
        InfoSection(
            title = "About Me",
            content = aboutMe,
            titleColor = Color.Blue
        )
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: String,
    titleColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = titleColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = content,
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProfileBadgePreview() {
    ProfileComponent(viewModel = HomeVM())
}
