package com.example.iec.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.iec.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class NetworkService : Service() {


    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        // Foreground Service must show notification
        // set channel notification id for android above O
        createNotificationChannelID()
        enableJob()
    }
    private fun createNotificationChannelID(){
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                )
            )
    }
    private fun enableJob(){
        scope.launch {
            var startTime = System.currentTimeMillis()
            while (true){
                val currentTime = System.currentTimeMillis()
                if (currentTime - startTime >= 1000){
                    startTime = currentTime
                    showNotification(startTime)
                }
            }
        }
    }
    private fun showNotification(timer: Long){
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ptit_iec)
            .setContentTitle(resources.getString(R.string.app_name) + " đềm nè")
            .setContentText(timer.toString())
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(111, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
    companion object {
        const val CHANNEL_ID = "Channel_002"
        const val CHANNEL_NAME = "Channel_Counter"
    }
}