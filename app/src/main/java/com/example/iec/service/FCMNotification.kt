package com.example.iec.service

import com.google.firebase.messaging.FirebaseMessagingService

class FCMNotification : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
}