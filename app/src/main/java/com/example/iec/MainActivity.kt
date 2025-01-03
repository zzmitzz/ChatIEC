package com.example.iec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.iec.ui.feature.authorise.LoginNavigation
import com.example.iec.ui.feature.authorise.LoginRoute
import com.example.iec.ui.feature.authorise.LoginScreen
import com.example.iec.ui.feature.authorise.LoginViewModel
import com.example.iec.ui.theme.IECTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IECTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginNavigation {
                        Unit
                    }
                }
            }
        }
    }
}
