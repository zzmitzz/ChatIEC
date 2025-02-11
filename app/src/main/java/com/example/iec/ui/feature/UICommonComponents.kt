package com.example.iec.ui.feature

import android.graphics.BlurMaskFilter
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.iec.R
import com.example.iec.ui.theme.ButtonBackground


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String = "",
    onValueChange: ((String) -> Unit),
    placeholder: String,
    isBordered: Boolean = false,
    isHidden: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                shape = RoundedCornerShape(12.dp),
                width = 2.dp,
                color = if (isBordered) Color(0xFF1F41BB) else Color.Transparent
            ),
        placeholder = {
            Text(
                text = placeholder,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .alpha(0.5f),
                fontSize = 16.sp
            )
        },
        singleLine = true,
        visualTransformation = if (isHidden) PasswordVisualTransformation() else VisualTransformation.None,
//        colors = TextFieldDefaults.textFieldColors(
//            disabledTextColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            containerColor = Color(0xFFF1F4FF)
//        )
    )
}

@Composable
fun LoadingDialog(isLoading: Boolean = true, isCritical: Boolean = false) {
    if (isCritical) {
        BackHandler(enabled = true) {}
    }
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Dialog(
            onDismissRequest = {}
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = Color.White
                    )
                    .padding(12.dp)
                    // Block unintended taps behind the full-screen Box
                    .pointerInput(Unit) {
                        detectTapGestures { }
                    }
                    .semantics {
                        contentDescription = "Processing..."
                        stateDescription = "Please wait"
                    }
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    val openLoading by remember { mutableStateOf(true) }
    LoadingDialog()
}

@Composable
fun SimpleButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = ButtonBackground
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable {
                    onClick()
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            ) {
                content.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun CustomDialogPreview() {
    CustomDialog(showDialog = true) {}
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit = {},
    title: String = "Note",
    content: @Composable () -> Unit,
) {
    val localConfig = LocalConfiguration.current
    val widthScreen = localConfig.screenWidthDp
    val heightScreen = localConfig.screenHeightDp

    if (showDialog) {

        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White
            )
        ) {
            Dialog(
                onDismissRequest = onDismissRequest,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val (close, iconBrand) = createRefs()
                        Text(
                            text = title,
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .constrainAs(iconBrand) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                                .clickable {
                                    onDismissRequest()
                                }
                                .constrainAs(close) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                },
                            tint = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = Color(0xFFEDEEF3)),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        content.invoke()
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    ShimmerText(
                        text = "Made with ❤ by PTIT IEC",
                        textStyle = TextStyle.Default.copy(
                            color = Color.DarkGray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        easing = EaseInOutBack
                    )
                }
            }
        }
    }
}

@Composable
fun Canvas1() {
    val listOffset = listOf(
        Offset(0f, 0f),
        Offset(50f, 150f),
        Offset(0f, 200f),
        Offset(0f, 300f),
    )
    Canvas(
        modifier = Modifier.size(200.dp)
    ) {
        drawRect(
            color = Color.Red,
            topLeft = listOffset[0],
            size = Size(100f, 100f),
        )
        drawCircle(
            color = Color.Blue,
            center = listOffset[1],
            radius = 50f
        )
        drawLine(
            color = Color.Green,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = 5f,
            cap = StrokeCap.Round
        )

        drawArc(
            color = Color.Yellow,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = true,
            topLeft = Offset(50f, 50f),
            size = Size(100f, 100f)
        )
    }
}

@Composable
fun Canvas2() {
    Canvas(
        modifier = Modifier.size(300.dp)
    ) {
        val path = Path().apply {
            moveTo(200f, 200f)
            lineTo(100f, 100f)
            quadraticTo(200f, 0f, 300f, 100f)
            close()
        }

        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            ),
        )

        rotate(45f) {
            drawRect(Color.Red, Offset(100f, 100f), Size(100f, 100f))
        }

        scale(0.5f) {
            drawCircle(Color.Blue, radius = 100f)
        }

        translate(left = 100f, top = 100f) {
            drawCircle(Color.Green, radius = 50f)
        }
    }

    Canvas(modifier = Modifier.size(200.dp)) {
        // Clipping
        clipRect(left = 0f, top = 0f, right = 300f, bottom = 300f) {
            drawCircle(Color.Red, radius = 100f)
        }

        // Blend Modes
        drawCircle(
            color = Color.Gray,
            radius = 50f,
            blendMode = BlendMode.Screen
        )

//        // Gradients
//        val gradient = Brush.linearGradient(
//            colors = listOf(Color.Red, Color.Blue),
//            start = Offset(0f, 0f),
//            end = Offset(size.width, size.height)
//        )
//
//        drawRect(brush = gradient)
    }
}

@Composable
fun ChipCard() {
    Canvas(
        modifier = Modifier.size(50.dp)
    ) {
        drawCircle(
            color = Color.Cyan,
            radius = 50f,
            center = Offset(50f, 50f)
        )
    }
}


enum class CardType(
    val color: Color,
    val linkQR: String,
    val localLogo: Int = 0,
    val localImage: Int = 0
) {
    MBBank(
        Color(0xFF1F41BB), "1", R.drawable.logo_mb_new, com.example.camera_ml.R.drawable.mb
    ),
    Momo(
        Color(0xFFA50064), "2", R.drawable.unnamed, R.drawable.unnamed
    ),
    ZaloPay(
        Color(0xFF013ED0), "3", R.drawable.zalo, com.example.camera_ml.R.drawable.zalo_pay
    )
}

@Composable
fun BankCard(
    cardType: CardType,
    scale: Float = 1f
) {
    Card(
        modifier = Modifier
            .height(240.dp)
            .width(160.dp)
            .scale(scale),
        colors = CardDefaults.cardColors().copy(
            containerColor = cardType.color
        ),
        border = BorderStroke(4.dp, Color.White),
        shape = RoundedCornerShape(12.dp),

        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = cardType.localLogo),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp))
        }
    }
}

@Preview
@Composable
private fun BankCardPreview() {
    BankCard(cardType = CardType.MBBank)
}

@Composable
fun Modifier.dropShadow(
    shape: Shape,
    color: Color,
    offsetX: Dp,
    offsetY: Dp,
    blur: Dp,
    spread: Dp,
    animateFloat: State<Float>? = rememberInfiniteTransition(label = "").animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
) = this.drawBehind {
    val shadowSize = Size(
        size.width + spread.toPx() + (animateFloat?.value ?: 0f),
        size.height + spread.toPx() + (animateFloat?.value ?: 0f)
    )
    val outlineShadow = shape.createOutline(shadowSize, layoutDirection, this)
    // Create a Paint object
    val paint = Paint()
// Apply specified color
    paint.color = color

// Check for valid blur radius
    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            // Apply blur to the Paint
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }
    drawIntoCanvas { canvas ->
        // Save the canvas state
        canvas.save()
        // Translate to specified offsets
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        // Draw the shadow
        canvas.drawOutline(outlineShadow, paint)
        // Restore the canvas state
        canvas.restore()
    }
}

@Composable
fun ShimmerText(
    text: String = "Ngo Tuan Anh",
    textStyle: TextStyle = TextStyle.Default.copy(color = Color.Black),
    shimmerColors: List<Color> = listOf(
        Color.Black,
        Color.DarkGray.copy(0.5f)
    ),
    easing: Easing = EaseInOut,
    animationSpec: State<Float> = rememberInfiniteTransition(label = "shimmer").animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = easing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
) {
    val brush = remember(animationSpec) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                // Define the starting X offset, beginning outside the left edge of the text
                val initialXOffset = -size.width
                // Total distance the shimmer will sweep across (double the text width for full coverage)
                val totalSweepDistance = size.width * 2
                // Calculate the current position of the shimmer based on the animation progress
                val currentPosition = initialXOffset + totalSweepDistance * animationSpec.value

                return LinearGradientShader(
                    colors = shimmerColors,
                    from = Offset(0f, 0f),
                    to = Offset(animationSpec.value, 0f)
                )
            }

        }
    }
    Text(
        text = text,
        modifier = Modifier.wrapContentSize(),
        style = textStyle.copy(brush),
    )
}

@Preview(
    backgroundColor = android.graphics.Color.WHITE.toLong(), showBackground = true
)
@Composable
fun AllPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        ShimmerText(
            text = "LOADING",
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                letterSpacing = 5.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}