package com.example.iec.ui.feature.main.checkin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iec.R

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
                    tint = Color.Black,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Nguyễn Trãi, Hà Đông, Hà Nội",
                    color = Color.Red
                )
            }
            Text(
                text = "Fair conference",
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.align(
                Alignment.CenterVertically
            ).padding(horizontal = 8.dp)
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.scanner),
                contentDescription = ""
            )
        }
    }
}
@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong()
)
@Composable
fun Preview(){
    TopBarHome()
}