package com.example.iec.ui.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BorderRadiosCard(
  backgroundColor: Color = Color.Black,
  borderColor: Color = Color.Red,
  elevation: Dp = 4.dp,
  onClick: (Int) -> Unit = {},
  content: @Composable () -> Unit
){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .background(color = Color.DarkGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        shape = RoundedCornerShape(8.dp)
    ){
        Box(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        ){
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun BorderRadiosCardPreview() {
    BorderRadiosCard(){
        Text(
            modifier = Modifier,
            text = "Xin chao",

        )
    }
}
