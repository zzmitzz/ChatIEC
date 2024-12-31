package com.example.iec.ui.feature.main.home

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun TopBarHome(
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onChangeLocation: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).weight(1f)
                .clickable {
                    onChangeLocation()
                }
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    tint = Color.White,
                    contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Gia Lâm, Hà Nội",
                    color = Color.Red
                )
            }
            Text(
                text = "IEC Cinema",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.align(
                Alignment.CenterVertically
            ).padding(horizontal = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, tint = Color.White, contentDescription = "",
                modifier = Modifier.clickable {
                    onSearchClick()
                })
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Rounded.Notifications, tint = Color.White, contentDescription = "",
                modifier = Modifier.clickable {
                    onNotificationClick()
                })
        }
    }
}

//fun


@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun TopBarHomePreview() {
    TopBarHome()
}