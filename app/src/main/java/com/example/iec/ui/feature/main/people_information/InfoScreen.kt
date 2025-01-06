//package com.example.iec.ui.feature.main.people_information
//
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.constraintlayout.compose.ConstraintLayout
//import com.example.iec.R
//import com.example.iec.ui.theme.BottomColorBg
//import com.example.iec.ui.theme.TopColorBg
//
//
//@Composable
//fun InfoScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        TopColorBg,  // Pink
//                        BottomColorBg,  // Purple
//                    )
//                )
//            )
//    ) {
//        Column(
//            modifier = Modifier.align(Alignment.Center)
//        ) {
//            var isFront: Boolean = false
//
//            val rotateY by animateFloatAsState(
//                targetValue = if (isFront) 180f else 0f,
//                animationSpec = tween(500)
//            )
//
//            val animateFront by animateFloatAsState(
//                targetValue = if (isFront) 0f else 1f,
//                animationSpec = tween(500)
//            )
//
//            val animateBack by animateFloatAsState(
//                targetValue = if (isFront) 1f else 0f,
//                animationSpec = tween(500)
//            )
//
//
//            Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
//                RoundedAvatar()
//            }
//            InfoCard()
//        }
//    }
//}
//
//
//@Composable
//fun InformationLine(
//    icon: Painter,
//    title: String,
//    customWeight: FontWeight = FontWeight.Bold,
//    customStyle: FontStyle = FontStyle.Italic,
//    name: String? = null
//) {
//    Box(){
//        Row(
//            modifier = Modifier
//                .wrapContentSize()
//                .padding(16.dp)
//                .align(Alignment.Center),
//            horizontalArrangement = Arrangement.Center
//        ){
//            Image(
//                modifier = Modifier.size(24.dp),
//                painter = icon,
//                contentDescription = "Icon"
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                modifier = Modifier.align(Alignment.CenterVertically),
//                text = title,
//                fontSize = 16.sp,
//                fontWeight = customWeight,
//                fontStyle = if(name?.equals("Quotes") == true) customStyle else FontStyle.Normal
//            )
//        }
//    }
//}
//
//@Composable
//fun InfoCard() {
//    val rectangleSize = LocalConfiguration.current.screenWidthDp
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp)
//            .padding(horizontal = 16.dp, vertical = 16.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .border(2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
//            .background(Color.White)
//    ){
//        ConstraintLayout(
//            modifier = Modifier.align(Alignment.CenterVertically)
//        ) {
//            val startGuideline = createGuidelineFromStart(32.dp)
//            val topGuideLine = createGuidelineFromTop(8.dp)
//            val (profile, birth, job, quotes) = createRefs()
//            Box(
//                modifier = Modifier.wrapContentSize().constrainAs(profile){
//                    start.linkTo(startGuideline)
//                    top.linkTo(topGuideLine)
//                }
//            ){
//                InformationLine(
//                    icon = painterResource(R.drawable.profile),
//                    name = "Profile",
//                    title = "Ngo Tuan Anh"
//                )
//            }
//            Box(
//                modifier = Modifier.constrainAs(birth){
//                    start.linkTo(startGuideline)
//                    top.linkTo(profile.bottom)
//                }.wrapContentSize()
//            ){
//                InformationLine(
//                    icon = painterResource(R.drawable.calendar),
//                    name = "Birth",
//                    title = "01-01-2000"
//                )
//            }
//            Box(modifier = Modifier.constrainAs(job){
//                start.linkTo(startGuideline)
//                top.linkTo(birth.bottom)
//            }.wrapContentSize()){
//                InformationLine(
//                    icon = painterResource(R.drawable.portfolio),
//                    name = "Job",
//                    title = "Software Engineer"
//                )
//            }
//            Box(modifier = Modifier.constrainAs(quotes){
//                start.linkTo(startGuideline)
//                top.linkTo(job.bottom)
//            }.wrapContentSize()){
//                InformationLine(
//                    icon = painterResource(R.drawable.face_filled),
//                    name = "Quotes",
//                    title = "“Hard working make you exhausted”"
//                )
//            }
//
//
//
//        }
//    }
//}
//
//
//
//
//@Composable
//fun InfoWithQR(){
//    val config = LocalConfiguration.current
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp).clip(RoundedCornerShape(16.dp))
//            .border(2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
//            .padding(horizontal = 6.dp, vertical = 8.dp)
//            .background(color = Color.White)
//    ){
//        Image(
//            modifier = Modifier.size(
//                (config.screenWidthDp/2).dp
//            ).align(Alignment.CenterVertically),
//            painter = painterResource(R.drawable.qr),
//            contentDescription = "QRCode"
//        )
//    }
//}
//
//@Preview(
//    showBackground = true
//)
//@Composable
//fun InfoScreenPreview() {
//    InfoScreen()
//}