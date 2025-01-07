package com.example.iec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.iec.ui.IECApp
import com.example.iec.ui.feature.main.home.HomeScreen
import com.example.iec.ui.theme.IECTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IECTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IECApp()
                }
            }
        }
    }
}
