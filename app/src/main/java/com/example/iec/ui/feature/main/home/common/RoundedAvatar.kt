package com.example.iec.ui.feature.main.home.common

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.example.iec.R
import com.example.iec.ui.feature.LoadingDialog

@Composable
fun RoundedAvatar(
    size: Int = 120,
    imagePathDrawable: Int = R.drawable.avatarasdfds,
    imagePathURL: String?,
    layerOnTopContent: @Composable () -> Unit = {}
) {
    var isLoading by  remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(size.dp)
            .background(Color.Transparent),
        shape = CircleShape, elevation = CardDefaults.cardElevation(8.dp),
    ) {
        if(isLoading){
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        val stateManage: (AsyncImagePainter.State) -> Unit = {state ->
            isLoading = when(state){
                is AsyncImagePainter.State.Loading -> {
                    true
                }

                is AsyncImagePainter.State.Error -> {
                    false
                }

                is AsyncImagePainter.State.Success -> {
                    false
                }

                AsyncImagePainter.State.Empty -> TODO()
            }
        }
        AsyncImage(
            model = imagePathURL,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            onState = stateManage,
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
        )
        layerOnTopContent()
    }
}