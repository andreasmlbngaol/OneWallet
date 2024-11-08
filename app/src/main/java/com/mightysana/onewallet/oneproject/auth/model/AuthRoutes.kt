package com.mightysana.onewallet.oneproject.auth.model

import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.presentation.email_verification.EmailVerificationScreen
import com.mightysana.onewallet.oneproject.auth.presentation.register.RegisterScreen
import com.mightysana.onewallet.oneproject.auth.presentation.sign_in.SignInScreen
import com.mightysana.onewallet.oneproject.auth.presentation.sign_up.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object AuthGraph

@Serializable
object EmailVerification

@Serializable
object Register

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    startDestination: Any
) {
    navigation<AuthGraph>(
        startDestination = startDestination
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
            EmailVerificationScreen(
                navController = navController
            )
        }

        composable<Register> {
            RegisterScreen(
                navController = navController
            )
        }
    }
}