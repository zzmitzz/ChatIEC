package com.example.iec.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.iec.core.network.IECSocketManager
import com.example.iec.service.NetworkService
import com.example.iec.ui.feature.IECApp
import com.example.iec.ui.feature.IECAppState
import com.example.iec.ui.feature.authorise.LoginNavigation
import com.example.iec.ui.theme.IECTheme
import com.google.firebase.messaging.FirebaseMessaging
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ){ isGranted ->
        if(isGranted){
            // TODO
        }else{

        }
    }
    @Inject
    lateinit var iecSocketManager: IECSocketManager
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appState = IECAppState(navController, iecSocketManager)
            IECTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    IECApp(appState, navController)
                }
            }
        }
        getNotificationPermission()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", token)
            }
        }
//        setUpConnect()
        startForegroundService(Intent(this, NetworkService::class.java))
    }
    private fun getNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, NetworkService::class.java))
    }

    private fun setUpConnect(){
        val options = PusherOptions()
        options.setCluster("ap1");

        val pusher = Pusher("a3ec8480ad22f43b4cf9", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
            }
        }, ConnectionState.ALL)

        val channel = pusher.subscribe("my-channel")
        channel.bind("my-event") { event ->
            Log.i("Pusher","Received event with data: $event")
        }

    }
}



fun Context.getActivity(): ComponentActivity?{
    return when(this){
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}
