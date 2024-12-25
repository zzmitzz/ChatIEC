package com.example.iec.ui.feature.people_information

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.iec.R


fun InfoScreenStateful(){

}

@Composable
fun InfoScreen(){
    Scaffold(
        topBar = {
            TopAppBar()
        },
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) { innerPadding ->
        ConstraintLayout() { }
    }
}


@Composable
fun RoundedAvatar() {
    Card(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Transparent),
        shape = CircleShape, elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
        )
    }
}


@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}