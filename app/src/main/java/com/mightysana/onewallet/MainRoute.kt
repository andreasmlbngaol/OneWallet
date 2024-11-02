package com.mightysana.onewallet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.main.presentation.home.HomeScreen
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.SignUp
import com.mightysana.onewallet.oneproject.auth.presentation.email_verification.EmailVerification
import com.mightysana.onewallet.oneproject.auth.presentation.sign_in.SignInScreen
import com.mightysana.onewallet.oneproject.auth.presentation.sign_up.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object Home


@Composable
fun MyAppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    reloadUser()

    // Tambah untuk registered
    val startDestination: Any = if(isUserLoggedIn() && isUserVerified()) Home
    else if(isUserLoggedIn()) EmailVerification
    else SignIn

    NavHost(
        navController = navController,
        startDestination = startDestination,
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

        composable<EmailVerification> {
            EmailVerification(
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

fun reloadUser() { Firebase.auth.currentUser?.reload() }

fun isUserLoggedIn(): Boolean = Firebase.auth.currentUser.isNotNull()

fun isUserVerified(): Boolean = Firebase.auth.currentUser!!.isEmailVerified

fun Any?.isNotNull(): Boolean = this != null