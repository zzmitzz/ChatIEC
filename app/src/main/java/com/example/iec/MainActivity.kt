package com.example.iec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iec.navigation.ScreenDestinationLevel
import com.example.iec.ui.feature.message.ModernChatScreen
import com.example.iec.ui.theme.IecTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IecTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(){
    val myNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = myNavController) }
    ) { paddingValues ->

    }
}