package com.example.iec.ui.feature.main.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.iec.ui.feature.DestinationRoute
import com.example.iec.ui.feature.IECAppState
import com.example.iec.ui.feature.main.home.common.EditProfileScreen


// The only screen that follow the nested navigation, just for practice, not have time for it.
sealed class HomeNavRoute(val route: String){
    data object Home:  HomeNavRoute("home/main")
    data object EditProfile: HomeNavRoute("editProfile/${USER_ID}"){
        fun createRoute(userID: String) = "editProfile/$userID"
    }
    companion object {
        const val USER_ID = "userID"
    }
}


@Composable
fun HomeNavigation(
    appState: IECAppState
) {
    val homeVM: HomeVM = hiltViewModel()
    val homeNavController = rememberNavController()

    val navigateToEditProfile: (String) -> Unit = { username ->
        homeNavController.navigate(HomeNavRoute.EditProfile.createRoute(username)) {
            launchSingleTop = true
            restoreState = true
        }
    }
    NavHost(
        navController = homeNavController,
        startDestination = "home"
    ){
        navigation(
            route = "home",
            startDestination = "home/main",
        ){
            composable(route = HomeNavRoute.Home.route,
                arguments = listOf(
                    navArgument(
                        name = HomeNavRoute.USER_ID
                    ){
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                }
            ) {
                HomeScreenStateful(
                    viewModel = homeVM,
                    navToEditProfile = navigateToEditProfile,
                    backPressed = appState::navigateBack,
                    logoutAction = appState::navToLogin,
                    setBottomBar = appState::setBottomBarVisible
                )
            }

            composable(
                route = HomeNavRoute.EditProfile.route,
            ) {
                EditProfileScreen(){
                    homeNavController.navigateUp()
                }
            }
        }

    }
}