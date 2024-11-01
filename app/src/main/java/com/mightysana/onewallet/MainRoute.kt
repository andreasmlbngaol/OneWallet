package com.mightysana.onewallet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mightysana.onewallet.auth.presentation.login.LoginScreen
import com.mightysana.onewallet.auth.presentation.register.RegisterScreen
import com.mightysana.onewallet.main.presentation.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

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
            LoginScreen(
                iconLauncher = painterResource(R.drawable.one_wallet_logo_round),
                navController = navController
            )
        }

        composable<Register> {
            RegisterScreen(
                iconLauncher = painterResource(R.drawable.one_wallet_logo_round),
                navController = navController
            )
        }

        composable<Home> {
            HomeScreen(
                navController = navController
            )
        }
    }
}

fun NavHostController.navigateAndPopUp(route: Any, popUp: Any) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}