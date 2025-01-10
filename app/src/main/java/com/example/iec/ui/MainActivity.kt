package com.example.iec.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.iec.state.ApplicationStateHolder
import com.example.iec.ui.feature.IECApp
import com.example.iec.ui.theme.IECTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var applicationStateHolder : ApplicationStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IECTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    IECApp(applicationStateHolder)
                }
            }
        }
    }
}
