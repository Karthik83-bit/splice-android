package com.project.splice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.splice.auth.EuropeTripDetailScreen
import com.project.splice.auth.LoginScreen
import com.project.splice.auth.SignupScreen
import com.project.splice.auth.SplitwiseAIHomeScreen

object Screens {
    val LoginScreen = "LoginScreen"
    val SignupScreen = "SignupScreen"
    val HomeScreen = "HomeScreen"
    val GroupScreen = "GroupScreen"
}

@Composable
fun NavWrapper(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.LoginScreen) {
        composable(Screens.LoginScreen) {
            LoginScreen(navController = navController)
        }
        composable(Screens.SignupScreen) {
            SignupScreen(navController = navController)
        }
        composable(Screens.HomeScreen) {
            SplitwiseAIHomeScreen(navController = navController)
        }
        composable(Screens.GroupScreen) {
            EuropeTripDetailScreen()
        }
    }
}
