package com.example.iec.ui.feature.people_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.iec.R
import com.example.iec.ui.model.UserInfo


@Composable
fun TopAppBar(
    onMenuClick: () -> Unit = {},
    onAddNewClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(color = Color.Blue)
            .padding(4.dp)

    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (menu, title, find) = createRefs()
            Icon(modifier = Modifier
                .padding(4.dp)
                .constrainAs(menu) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
                imageVector = Icons.Default.Menu,
                tint = Color.White,
                contentDescription = "Menu"
            )
            Text(
                text = "Thông tin nè",
                color = Color.White,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier
                    .padding(4.dp)
                    .constrainAs(find) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

        }

    }
}

@Composable
fun MainCard(userInfo: UserInfo){
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Row {

            }
        }
    }
}



@Preview
@Composable
fun TopAppBarPreview() {
    RoundedAvatar()
}