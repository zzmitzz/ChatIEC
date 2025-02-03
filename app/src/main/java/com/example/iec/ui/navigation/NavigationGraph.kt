package com.example.iec.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.iec.ui.feature.DestinationRoute
import com.example.iec.ui.feature.IECAppState
import com.example.iec.ui.feature.authorise.LoginNavigation
import com.example.iec.ui.feature.authorise.LoginScreen
import com.example.iec.ui.feature.authorise.RegisterScreen
import com.example.iec.ui.feature.main.home.HomeNavigation
import com.example.iec.ui.feature.main.home.common.EditProfileScreen
import com.example.iec.ui.feature.main.message.ChatMessageVM
import com.example.iec.ui.feature.main.message.box_chat_message.ModernChatScreen
import com.example.iec.ui.feature.main.message.list_chat.ListChatScreen
import com.example.iec.ui.feature.main.tools.ToolScreen
import com.example.iec.ui.theme.ColorPrimary


@Composable
fun NavigationGraph(
    navController: NavHostController,
    iecAppState: IECAppState,
    hideBottomBar: () -> Unit,
    showBottomBar: () -> Unit
) {
    NavHost(navController = navController, startDestination = "authentication") {


        // Authenticate Route
        navigation(
            startDestination = DestinationRoute.Login.route,
            route = "authentication"
        ) {
            composable(route = DestinationRoute.Login.route) {
                hideBottomBar()
                LoginNavigation(
                    navigateToHome = { iecAppState.navToHome() },
                    navigateToRegister = { iecAppState.navToRegister() })
            }
            composable(route = DestinationRoute.Register.route) {
                hideBottomBar()
                RegisterScreen(
                    viewModel = hiltViewModel()
                )
            }
        }


        // Main route
        navigation(
            startDestination = DestinationRoute.Home.route,
            route = "main",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {

            composable(route = DestinationRoute.Home.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                },
                arguments = listOf(
                    navArgument(
                        name = DestinationRoute.AGR_HOME_USER_ID,
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )) {
                showBottomBar()
                HomeNavigation(
                    appState = iecAppState
                )
            }
            composable(
                route = DestinationRoute.EditUserInfo.route,
                arguments = listOf(
                    navArgument(
                        name = DestinationRoute.AGR_HOME_USER_ID
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
            ) {
                hideBottomBar()
                EditProfileScreen(
                    onBackPress = { iecAppState.navigateBack() }
                ) {

                }
            }
            composable(route = DestinationRoute.Tools.route) {
                showBottomBar()
                ToolScreen()
            }
            composable(route = DestinationRoute.Message.route) {
                showBottomBar()
                ListChatScreen(
                    navToMessageID = { userName ->
                        iecAppState.navigateToMessagePersonal(userName)
                    }
                )
            }

            composable(route = DestinationRoute.MessagePersonal.route,
                arguments = listOf(
                    navArgument(
                        name = DestinationRoute.AGR_MESS_ID
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(300)
                    )
                }
            ) {
                hideBottomBar()
                ModernChatScreen(
                    onBackPress = { iecAppState.navigateBack() }
                )
            }
            composable(route = DestinationRoute.FaceRecognition.route) {

                showBottomBar()
                ConstraintLayout() {
                    val (text) = createRefs()
                    Text(
                        text = "4",
                        color = ColorPrimary,
                        modifier = Modifier.constrainAs(text) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
        }
    }
}
