package com.example.iec.ui.feature.main.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.iec.ui.feature.IECAppState


@Composable
fun HomeNavigation(
    appState: IECAppState
) {
    val homeVM: HomeVM = hiltViewModel()
    val navigateEditScreen: (String) -> Unit = { userID ->
        appState.navToEditProfile(userID)
    }
    HomeScreenStateful(homeVM, navigateEditScreen, logoutAction = { appState.navToLogin() })
}