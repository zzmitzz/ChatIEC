package com.example.iec.ui.feature.people_information

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.iec.R
import com.example.iec.ui.theme.BottomColorBg
import com.example.iec.ui.theme.TopColorBg


@Composable
fun InfoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TopColorBg,  // Pink
                        BottomColorBg,  // Purple
                    )
                )
            )
    ) {
        Column {
//            val (roundAvatar, infoCard) = createRefs()
            RoundedAvatar()
            InfoCard()
        }
    }
}

@Composable
fun InfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .background(Color.White)
    ){
        Column {
            InformationLine(
                icon = painterResource(R.drawable.profile),
                title = "Ngo Tuan Anh"
            )
            InformationLine(
                icon = painterResource(R.drawable.calendar),
                title = "01-01-2000"
            )
            InformationLine(
                icon = painterResource(R.drawable.portfolio),
                title = "Software Engineer"
            )
            InformationLine(
                icon = painterResource(R.drawable.face_filled),
                title = "“Hard working make you exhausted”"
            )
        }
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

@Composable
fun InformationLine(
    icon: Painter,
    title: String
) {
    Box(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier.size(24.dp),
                painter = icon,
                contentDescription = "Icon"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Preview(
    showBackground = true

)
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}