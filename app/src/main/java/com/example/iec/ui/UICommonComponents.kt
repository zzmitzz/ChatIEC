package com.example.iec.ui

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iec.ui.theme.ButtonBackground
import com.example.iec.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String = "", onValueChange: ((String) -> Unit), placeholder: String, isBordered: Boolean = false
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
                modifier = Modifier.padding(horizontal = 8.dp).alpha(0.5f),
                fontSize = 16.sp
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            containerColor = Color(0xFFF1F4FF)
        )
    )
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
            color =ButtonBackground
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
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            ){
                content.invoke()
            }
        }
    }
}

@Preview(
    backgroundColor = android.graphics.Color.WHITE.toLong(), showBackground = true
)
@Composable
fun CustomTextFieldPreview() {
    SimpleButton(
        onClick = {}
    ) {
        Text(
            text = "Sign in",
            color = Color.White,
            modifier = Modifier
        )
    }
}