package com.mightysana.onewallet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.mightysana.onewallet.auth.presentation.sign_in.SignInScreen
import com.mightysana.onewallet.auth.presentation.sign_up.SignUpScreen
import com.mightysana.onewallet.main.presentation.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Composable
fun MyAppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = if(isUserLoggedIn()) Home else SignIn,
        modifier = modifier
    ) {
        composable<SignIn> {
            SignInScreen(
                iconLauncher = painterResource(R.drawable.one_wallet_logo_round),
                navController = navController
            )
        }

        composable<SignUp> {
            SignUpScreen(
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

fun isUserLoggedIn(): Boolean = FirebaseAuth.getInstance().currentUser.isNotNull()

fun Any?.isNotNull(): Boolean = this != null