package com.example.iec.ui.feature.main.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeNavigation(){
    val homeNavController = rememberNavController()
    val homeVM: HomeVM = viewModel()

    HomeScreenStateful(homeVM, homeNavController)
}