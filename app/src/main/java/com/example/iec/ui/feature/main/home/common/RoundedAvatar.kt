package com.example.iec.ui.feature.main.home.common

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.iec.R

@Composable
fun RoundedAvatar(
    size: Int = 120,
    imagePathDrawable: Int = R.drawable.avatarasdfds,
    imagePathURL: Uri? = null,
    layerOnTopContent: @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(size.dp)
            .background(Color.Transparent),
        shape = CircleShape, elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Image(
            painter = painterResource(imagePathDrawable),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
        )
        layerOnTopContent()
    }
}