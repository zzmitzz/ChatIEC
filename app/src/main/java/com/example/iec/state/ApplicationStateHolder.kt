package com.example.iec.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class LoadingState (
    val isLoading: Boolean = false
)

interface LoadingStateHolder {
    val loadingState: StateFlow<LoadingState>
    fun offLoading(): Unit
    fun onLoading(): Unit
}

class LoadingStateHolderImpl @Inject constructor(): LoadingStateHolder {
    private var _loadingState = MutableStateFlow(LoadingState())
    override val loadingState: StateFlow<LoadingState> =  _loadingState
    override fun offLoading() {
        _loadingState.update{
            it.copy(isLoading = false)
        }
    }

    override fun onLoading() {
        _loadingState.update{
            it.copy(isLoading = true)
        }
    }
}

interface ApplicationStateHolder {
    val loadingStateHolder: LoadingStateHolder
}