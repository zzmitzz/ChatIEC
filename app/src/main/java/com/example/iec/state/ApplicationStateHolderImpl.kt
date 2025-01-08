package com.example.iec.state

import javax.inject.Inject

class ApplicationStateHolderImpl @Inject constructor(override val loadingStateHolder: LoadingStateHolder) :
    ApplicationStateHolder {
}