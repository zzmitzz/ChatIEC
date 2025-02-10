package com.example.iec.ui.feature.main.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.camera.QRReader
import com.example.camera.QRReaderML
import com.example.camera.QRResult
import com.example.iec.DataStoreHelper
import com.example.iec.DataStoreHelperImpl
import com.example.iec.PreferenceKeys
import com.example.iec.R
import com.example.iec.ui.feature.CustomDialog
import com.example.iec.ui.feature.ShimmerText
import com.example.iec.ui.feature.dropShadow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.math.roundToInt


@Composable
fun HomeScreenStateful(
    viewModel: HomeVM,
    navToEditProfile: (String) -> Unit,
    backPressed: () -> Unit = {},
    logoutAction: () -> Unit = {},
    setBottomBar: (Boolean) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    var overlayMenuVisibility by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope() + CoroutineExceptionHandler { _, throwable ->
        Log.d("HomeScreen: Coroutine exception", throwable.message.toString())
    }

    val qrScanner = QRReaderML(context)
    val dataStore = context.DataStoreHelper()

    var dialogShown by remember{
        mutableStateOf(
            false to ""
        )
    }
    LaunchedEffect(overlayMenuVisibility) {
        setBottomBar(!overlayMenuVisibility)
    }
    HomeScreenStateless(
        screenType = uiState.value.selectedHomeScreen,
        changeScreenType = viewModel::changeScreenType,
        onMenuOpen = {
            overlayMenuVisibility = !overlayMenuVisibility
        },
        onScanAction = {
            qrScanner.startScanCode().onEach {
                when (it) {
                    is QRResult.Success -> {
                        val result = it.result
                        dialogShown = (true to result)
                    }

                    is QRResult.Failed -> {
                        Toast.makeText(context, "Failed to scan QR Code", Toast.LENGTH_SHORT).show()
                    }

                    QRResult.Canceled -> {}
                }
            }.launchIn(coroutineScope)
        }
    )
    // Menu Home Action
    if (overlayMenuVisibility) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.95f)
                .clickable {
                    // Do nothing, just for consume the click of overlay.
                    overlayMenuVisibility = false
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
                        overlayMenuVisibility = false
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
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Log out",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        logoutAction()
                        coroutineScope.launch {
                            dataStore.clearData(
                                PreferenceKeys.USER_PASSWORD,
                                PreferenceKeys.USER_NAME
                            )
                        }

                    }
                )
            }
        }
    }

    CustomDialog(
        dialogShown.first,
        title = "IEC Scanner",
        onDismissRequest = {
            dialogShown = (false to "")
        }
    ) {
        Text(
            text = dialogShown.second,
            fontSize = 14.sp
        )
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenStateless(
    screenType: ProfileType = ProfileType.MY_WALLET,
    changeScreenType: (ProfileType) -> Unit = {},
    onMenuOpen: () -> Unit = {},
    onScanAction: () -> Unit = {}
) {
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
                                onMenuOpen.invoke()
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
                                    onClick = {
                                        onScanAction()
                                    }
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
            ComposablePickUp(screenType, changeScreenType)
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 32.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 16.dp, // Adjust the elevation as needed
                            shape = CardDefaults.shape, // Use the same shape as the card
                            clip = false, // Set to true if you want the shadow to be clipped to the shape
                            ambientColor = Color.White, // Ambient shadow color
                            spotColor = Color.White // Spot shadow color
                        )
                        .dropShadow(
                            shape = CardDefaults.shape,
                            offsetX = 0.dp,
                            offsetY = 0.dp,
                            blur = 32.dp,
                            spread = 0.dp,
                            color = Color(0xFFD1D1D1)
                        ),
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
                    when(screenType){
                        ProfileType.MY_WALLET -> {
                            MainComponents()
                        }

                        ProfileType.BIO -> {

                        }
                        ProfileType.CHECK -> {

                        }
                    }
                }
            }

        }
    }
}


@Composable
fun MainComponents() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (common, middle) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp)
                .constrainAs(common) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            val (avatar, divider, name, place, quotes) = createRefs()
            Box(
                modifier = Modifier
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                RoundedAvatarCard(size = 100.dp)
            }
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(100.dp)
                    .constrainAs(divider) {
                        start.linkTo(avatar.end)
                        top.linkTo(avatar.top)
                        bottom.linkTo(avatar.bottom)
                    }
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = Color.DarkGray)
            )
            Box(
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(divider.top)
                        start.linkTo(divider.end)
                    }
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                ShimmerText(
                    text = "Ngo Hao Nhan",
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 22.sp,
                        fontFamily = FontFamily.Default,
                        letterSpacing = 3.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = "Ha Noi",
                fontSize = 18.sp,
                color = Color(0xFF565050).copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .constrainAs(place) {
                        top.linkTo(name.bottom)
                        start.linkTo(divider.end)
                    }
                    .padding(start = 8.dp, top = 4.dp)
            )
            Text(
                text = "The only way to do great work is to love what you do",
                fontSize = 14.sp,
                color = Color(0xFF565050).copy(alpha = 0.8f),
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif
                ),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .constrainAs(quotes) {
                        top.linkTo(place.bottom)
                        start.linkTo(divider.end)
                        width = Dimension.preferredWrapContent
                        height = Dimension.preferredWrapContent
                    }
                    .width(180.dp)
                    .padding(start = 8.dp, top = 4.dp),
                maxLines = Int.MAX_VALUE, // Allows unlimited lines
                overflow = TextOverflow.Visible // Ensures no truncation
            )
        }
        Image(
            modifier = Modifier
                .scale(4f)
                .constrainAs(middle) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(bottom = 50.dp),
            painter = painterResource(R.drawable.wallet),
            contentDescription = "Wallet",
        )
    }
}


@Composable
fun ComposablePickUp(
    screenType: ProfileType,
    onClick: (ProfileType) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.clickable {
                onClick(ProfileType.MY_WALLET)
            }
        ) { CardHolder("My Wallet", screenType == ProfileType.MY_WALLET) }
        Text(
            text = "•",
            fontSize = 23.sp,
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier.clickable {
                onClick(ProfileType.BIO)
            }
        ) { CardHolder("Bio", screenType == ProfileType.BIO) }
        Text(
            text = "•",
            fontSize = 23.sp,
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier.clickable {
                onClick(ProfileType.CHECK)
            }
        ) {
            CardHolder("Check-in", screenType == ProfileType.CHECK)
        }
    }
}

@Composable
fun CardHolder(type: String, isChosen: Boolean = false) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(30.dp)
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = if (!isChosen) Color(0xFFD1D1D1) else Color.Black.copy(alpha = 0.9f)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                text = type,
                fontSize = 13.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = if (isChosen) Color.White else Color.DarkGray
            )
        }
    }
}


@Composable
fun RoundedAvatarCard(
    remoteImageUrl: String? = null,
    localImage: Int = R.drawable.wallpaperflare_com_wallpaper,
    size: Dp = 80.dp
) {
    Card(
        modifier = Modifier
            .size(size)
            .dropShadow(
                shape = CardDefaults.shape,
                color = Color.Black.copy(0.3f),
                offsetX = 0.dp,
                offsetY = 0.dp,
                blur = 32.dp,
                spread = 0.dp,
            ),
        shape = CircleShape
    ) {
        if (remoteImageUrl != null) {
            AsyncImage(
                model = remoteImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),

                contentScale = ContentScale.Fit
            )
        } else {
            Image(
                painter = painterResource(id = localImage),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    RoundedAvatarCard()
}

@Preview(showBackground = true)
@Composable
private fun Home2Preview() {
    HomeScreenStateless()
}

@Preview(showBackground = true)
@Composable
private fun Home3Preview() {
    MainComponents()
}