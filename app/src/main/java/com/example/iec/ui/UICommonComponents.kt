package com.example.iec.ui

import android.view.WindowManager
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.iec.R
import com.example.iec.ui.theme.ButtonBackground
import com.example.iec.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String = "",
    onValueChange: ((String) -> Unit),
    placeholder: String,
    isBordered: Boolean = false
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
fun LoadingDialog() {

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
        ) {
            CircularProgressIndicator()
        }
    }
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

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val localConfig = LocalConfiguration.current
    val widthScreen = localConfig.screenWidthDp
    val heightScreen = localConfig.screenHeightDp

    if (showDialog) {

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
                        .padding(8.dp)
                ) {
                    val (close, iconBrand) = createRefs()
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            .clickable {
                                onDismissRequest()
                            }
                            .constrainAs(close) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            },
                        tint = Color.DarkGray
                    )
                    Image(
                        painter = painterResource(R.drawable.ptit_iec),
                        modifier = Modifier
                            .size(45.dp)
                            .constrainAs(iconBrand) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            },
                        contentDescription = "PTIT",
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(color = Color(0xFFEDEEF3))

                ) {
                    content.invoke()
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Made with ❤️ by PTIT",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(
    backgroundColor = android.graphics.Color.WHITE.toLong(), showBackground = true
)
@Composable
fun AllPreview() {
    CustomDialog(true) {
        Box(
            modifier = Modifier.size(300.dp)
        )
    }
}