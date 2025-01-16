package com.example.iec.ui.feature.main.home.common

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.iec.R
import com.example.iec.ui.feature.CustomDialog
import com.example.iec.ui.feature.main.home.HomeUIState
import com.example.iec.ui.feature.main.home.HomeVM
import com.example.iec.ui.feature.main.home.ProfileType
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import qrcode.QRCode
import qrcode.color.Colors


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileComponent(
    screenType: ProfileType = ProfileType.CHECK,
    uiState: HomeUIState,
    navigateEditProfile: (String) -> Unit = {},
    onShare: () -> Unit = {},
    onDiscovery: (Boolean) -> Unit = {},
    onGetLocation: () -> Location = { Location(0.0, 0.0) }
) {
    var discoveryMode by remember { mutableStateOf(false) }
    var onCheckInDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                                navigateEditProfile("Ngo Tuan Anh")
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
            QRDisplayScreen(
                onCheckIn = {
                    onCheckInDialog = true
                },
                qrBitmap = (uiState as HomeUIState.HomeReady).qrCodeReceive)
        }
    }


    CustomDialog(
        showDialog = onCheckInDialog,
        onDismissRequest = { onCheckInDialog = false },
    ) {
        CheckInScreen(
            onGetLocationClick = { onGetLocation() },
            userName = "ZzMITzZ",
            onCheckInClick = {
                Toast.makeText(context, " ✅ Successful check-in", Toast.LENGTH_SHORT).show()
            }
        )
    }
}


@Composable
fun QRDisplayScreen(
    onCheckIn: () -> Unit = {},
    qrBitmap: Bitmap? = null, // QR Code Bitmap
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "FAIR",
            fontFamily = FontFamily.Cursive,
            fontSize = 24.sp
        )
        // QR Code Display
        Card(
            modifier = Modifier
                .padding(32.dp)
                .wrapContentSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            if(qrBitmap != null) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                    )
                }
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

//@Preview(
//    showBackground = true
//)
//@Composable
//fun ProfileBadgePreview() {
//    ProfileComponent(viewModel = HomeVM())
//}
