package com.example.iec

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IECApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}