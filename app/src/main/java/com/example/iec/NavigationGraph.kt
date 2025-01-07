package com.example.iec

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.iec.ui.feature.main.home.HomeNavigation
import com.example.iec.ui.theme.ColorPrimary


@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: ScreenDestinationLevel
) {
    NavHost(navController = navController, startDestination = "Home_Graph") {
        navigation(
            startDestination = "Home",
            route = "Home_Graph"
        ){
            composable(route = ScreenDestinationLevel.Home.route) {
                HomeNavigation()
            }
            composable(route = ScreenDestinationLevel.Translate.route) {
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
            composable(route = ScreenDestinationLevel.Message.route) {
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
            composable(route = ScreenDestinationLevel.FaceRecognise.route) {
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
