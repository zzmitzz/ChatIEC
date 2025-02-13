package com.example.iec.ui.feature.main.home

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.iec.ui.model.UserInfo
import com.example.iec.ui.theme.ColorPrimary
import com.example.iec.ui.theme.colorOnPrimary


@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(UserInfo())
}


@Composable
fun ProfileScreen(userInfo: UserInfo) {
    // Custom colors for dark theme
    val darkBackground = Color(0xFF121212)
    val surfaceColor = Color(0xFF1E1E1E)
    val accentColor = colorOnPrimary
    val gradientColors = listOf(
        Color(0xFF2A2A2A),
        colorOnPrimary
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .background(darkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorOnPrimary)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header section with profile image and gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = userInfo.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = ColorPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "@${userInfo.userName}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = ColorPrimary.copy(alpha = 0.7f)
                    )
                }
            }

            // Info cards
            Column (
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                InfoCard(
                    title = "About Me",
                    content = userInfo.aboutMe,
                    icon = Icons.Default.Person
                )

                InfoCard(
                    title = "Work",
                    content = "${userInfo.currentJob} at ${userInfo.workingPlace}",
                    icon = Icons.Default.DateRange
                )

                InfoCard(
                    title = "Location",
                    content = userInfo.city,
                    icon = Icons.Default.LocationOn
                )

                InfoCard(
                    title = "Personal Info",
                    content = "Birth: ${userInfo.birth}\nGender: ${userInfo.gender}\nStatus: ${userInfo.marriage}",
                    icon = Icons.Default.Info
                )

                if (userInfo.quotes.isNotBlank() && userInfo.quotes != "Unknown") {
                    InfoCard(
                        title = "Favorite Quote",
                        content = "\"${userInfo.quotes}\"",
                        icon = Icons.Default.DateRange
                    )
                }
            }

            Box(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    content: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorOnPrimary,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}