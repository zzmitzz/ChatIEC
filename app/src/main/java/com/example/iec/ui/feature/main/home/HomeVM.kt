package com.example.iec.ui.feature.main.home

import androidx.lifecycle.ViewModel
import com.example.iec.ui.feature.main.home.common.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(): ViewModel(){
    var allPermissionsGranted = false
    fun getLocation(): Location? {
        return null
    }
}