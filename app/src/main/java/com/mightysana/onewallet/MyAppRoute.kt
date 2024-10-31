package com.mightysana.onewallet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mightysana.onewallet.auth.presentation.login.LoginScreen
import com.mightysana.onewallet.auth.presentation.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Composable
fun MyAppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {
        composable<Login> {
            LoginScreen(navController = navController)
        }

        composable<Register> {
            RegisterScreen(navController = navController)
        }
    }
}

fun NavHostController.navigateAndPopUp(route: Any, popUp: Any) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}