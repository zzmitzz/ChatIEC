package com.example.iec.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
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
import com.example.iec.ui.feature.main.home.HomeNavigation
import com.example.iec.ui.feature.main.home.common.EditProfileScreen
import com.example.iec.ui.feature.main.message.box_chat_message.ModernChatScreen
import com.example.iec.ui.feature.main.tools.ToolScreen
import com.example.iec.ui.theme.ColorPrimary


@Composable
fun NavigationGraph(
    navController: NavHostController,
    iecAppState: IECAppState
) {
    NavHost(navController = navController, startDestination = "authentication") {

        navigation(
            startDestination = DestinationRoute.Login.route,
            route = "authentication"
        ){
            composable(route = DestinationRoute.Login.route) {
                LoginNavigation() {
                    iecAppState.navToHome("Ngo Tuan Anh")
                }
            }
        }


        navigation(
            startDestination = DestinationRoute.Home.route,
            route = "main"
        ) {


            composable(route = DestinationRoute.Home.route,
                arguments = listOf(
                    navArgument(
                        name = DestinationRoute.AGR_HOME_USER_ID,
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )) {
                HomeNavigation(
                    navigateEditScreen = { userID ->
                        iecAppState.navToEditProfile(userID)
                    }
                )
            }
            composable(route = DestinationRoute.EditUserInfo.route,
                arguments = listOf(
                    navArgument(
                        name = DestinationRoute.AGR_HOME_USER_ID
                    ) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                EditProfileScreen(
                    onBackPress = { iecAppState.navigateBack() }
                ) {

                }
            }
            composable(route = DestinationRoute.Tools.route) {
                ToolScreen()
            }
            composable(route = DestinationRoute.Message.route) {
                ModernChatScreen()
            }
            composable(route = DestinationRoute.FaceRecognition.route) {
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
