package com.example.iec.ui.feature.main.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.iec.ui.feature.IECAppState


@Composable
fun HomeNavigation(
    username: String,
    appState: IECAppState
){
    val homeVM: HomeVM = viewModel()
    val navigateEditScreen: (String) -> Unit = { userID ->
        appState.navToEditProfile(userID)
    }
    HomeScreenStateful(homeVM, navigateEditScreen, username = username)
}