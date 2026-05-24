package com.project.splice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.splice.auth.EuropeTripDetailScreen
import com.project.splice.auth.LuminaWelcomeScreen
import com.splitwiseai.app.ui.screens.SplitwiseAIHomeScreen

object Screens{
    val LoginScreen: String="FirstScreen"
            val HomeScreen="HomeScreen"
    val GroupScreen="GroupScreen"
}

@Composable
fun NavWrapper(modifier: Modifier = Modifier,navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.LoginScreen){
        composable (Screens.LoginScreen){
            LuminaWelcomeScreen(navController = navController)
        }
        composable(Screens.HomeScreen) {
            SplitwiseAIHomeScreen(navController = navController)
        }
        composable(Screens.GroupScreen) {
            EuropeTripDetailScreen()
        }
    }
}