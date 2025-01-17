package com.example.iec.ui.feature.main.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iec.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ToolVM @Inject constructor() : BaseViewModel() {

    fun fakeTimeJob(){
        viewModelScope.launch {
            setLoading(true)
            delay(2000)
            setLoading(false)
        }
    }
}