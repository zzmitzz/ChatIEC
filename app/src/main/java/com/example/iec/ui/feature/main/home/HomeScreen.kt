package com.example.iec.ui.feature.main.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.camera.QRReader
import com.example.camera.QRReaderML
import com.example.camera.QRResult
import com.example.iec.R
import com.example.iec.ui.CustomDialog
import com.example.iec.ui.feature.main.home.common.OtherPPInfoCard
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus


@Composable
fun HomeScreenStateful(
    viewModel: HomeVM,
    navController: NavController
) {
    var screenType by remember { mutableStateOf(ProfileType.PROFILE) }
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
        },
        onSaveEditProfile = {

        },
        viewModel = viewModel
    )
}


@Composable
fun HomeScreen(
    screenType: ProfileType = ProfileType.PROFILE,
    onScreenTypeChange: () -> Unit = {},
    onSaveEditProfile: () -> Unit = {},
    onGetLocation: () -> Unit = {},
    onActionCheckIn: () -> Unit = {},
    viewModel: HomeVM? = null
) {
    LaunchedEffect(screenType) {
        Log.d("HomeScreen", "Screen type changed to: $screenType")
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var onMenuOpened by remember { mutableStateOf(false) }
    val qrScanner: QRReader = QRReaderML(context)
    var qrContentResult by remember { mutableStateOf("") }
    val scopeContext =
        CoroutineScope(coroutineScope.coroutineContext) + CoroutineExceptionHandler { coroutineContext, throwable -> }
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
                    contentDescription = "Menu",
                    Modifier.clickable {
                        onMenuOpened = true
                    }
                )

                Row {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    qrScanner
                                        .startScanCode()
                                        .onEach {
                                            when (it) {
                                                is QRResult.Success -> {
                                                    qrContentResult = it.result
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "QR Code Scanned",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }

                                                is QRResult.Canceled -> {
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "QR Code Scanning Cancelled",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }

                                                is QRResult.Failed -> {
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "QR Code Scanning Failed",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            }
                                        }
                                        .launchIn(scope = scopeContext)
                                }
                            ),
                        painter = painterResource(R.drawable.scanner),
                        contentDescription = "",
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
                        onClick = {
                            if (screenType == ProfileType.CHECK)
                                onScreenTypeChange.invoke()
                        }
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
                            if (screenType == ProfileType.PROFILE)
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
                    screenType = screenType,
                    onSaveChange = { onSaveEditProfile() },
                    viewModel = viewModel!!
                )
            }
        }
    }


    if (qrContentResult.isNotEmpty()) {
        if(URLUtil.isValidUrl(qrContentResult)){
            CustomDialog(showDialog = true,
                onDismissRequest = {
                    qrContentResult = ""
                }) {
                var uriIntent: Intent? = null
                try{
                    uriIntent = Intent(Intent.ACTION_VIEW, Uri.parse(qrContentResult))
                }catch (e: Exception){
                }
                Text(
                    text = qrContentResult,
                    modifier = Modifier.padding(16.dp).clickable {
                        uriIntent?.let {
                            context.startActivity(it)
                        }
                    }
                )
            }
        }
        else{
            CustomDialog(showDialog = true,
                onDismissRequest = {
                    qrContentResult = ""
                }) {
                OtherPPInfoCard()
            }
        }

    }



    if (onMenuOpened) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.95f)
                .clickable {
                    // Do nothing, just for consume the click of overlay.
                    onMenuOpened = false
                }
                .background(color = Color.White),
        )
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val closeIcon = createRef()

            // Close Icon
            IconButton(
                onClick = { /* Handle close click */ },
                modifier = Modifier.constrainAs(closeIcon) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.clickable {
                        onMenuOpened = false
                    }
                )
            }

            // Column with text elements and spacers
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(
                    text = "Join Conference",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Quit Conference",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Help",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Delete Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Delete Account", Toast.LENGTH_SHORT).show()
                    }
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