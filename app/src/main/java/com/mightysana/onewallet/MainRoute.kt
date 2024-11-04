package com.mightysana.onewallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.main.presentation.home.HomeScreen
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.SignUp
import com.mightysana.onewallet.oneproject.auth.presentation.email_verification.EmailVerification
import com.mightysana.onewallet.oneproject.auth.presentation.register.RegisterScreen
import com.mightysana.onewallet.oneproject.auth.presentation.sign_in.SignInScreen
import com.mightysana.onewallet.oneproject.auth.presentation.sign_up.SignUpScreen
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.model.OneAppState
import kotlinx.serialization.Serializable

@Serializable
object Home


@Composable
fun MyAppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsState()
    val appState by viewModel.appState.collectAsState()
    OneScreen(appState) {
        if (appState == OneAppState.OKAY)
            NavHost(
                navController = navController,
                startDestination = startDestination!!,
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

                composable<EmailVerification> {
                    EmailVerification(
                        navController = navController
                    )
                }

                composable<Register> {
                    RegisterScreen(
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
}

fun NavHostController.navigateAndPopUp(route: Any, popUp: Any) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}

val currentUser = Firebase.auth.currentUser

fun Any?.isNotNull(): Boolean = this != null