package com.example.iec.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.iec.ui.feature.DestinationRoute
import com.example.iec.ui.feature.main.home.HomeNavigation
import com.example.iec.ui.theme.ColorPrimary


@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "main") {
        navigation(
            startDestination = DestinationRoute.Home.route,
            route = "main"
        ){
            composable(route = DestinationRoute.Home.route) {
                HomeNavigation()
            }
            composable(route = DestinationRoute.Tools.route) {
                ConstraintLayout(){
                    val (text) = createRefs()
                    Text(
                        text = "2",
                        color = ColorPrimary,
                        modifier = Modifier.constrainAs(text){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
            composable(route = DestinationRoute.Message.route) {
                ConstraintLayout(){
                    val (text) = createRefs()
                    Text(
                        text = "3",
                        color = ColorPrimary,
                        modifier = Modifier.constrainAs(text){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
            composable(route = DestinationRoute.FaceRecognition.route) {
                ConstraintLayout(){
                    val (text) = createRefs()
                    Text(
                        text = "4",
                        color = ColorPrimary,
                        modifier = Modifier.constrainAs(text){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
        }
    }
}
