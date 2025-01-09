package com.example.iec

import android.os.Bundle
import android.speech.SpeechRecognizer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.iec.state.ApplicationStateHolder
import com.example.iec.state.ApplicationStateHolderImpl
import com.example.iec.ui.CustomDialog
import com.example.iec.ui.IECApp
import com.example.iec.ui.feature.main.home.HomeScreen
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
