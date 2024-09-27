package com.mightysana.onewallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mightysana.onewallet.screens.home.HomeScreen
import com.mightysana.onewallet.screens.login.LoginScreen
import com.mightysana.onewallet.ui.theme.OneWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OneWalletTheme {
                OneWalletApp()
            }
        }
    }
}

@Composable
fun OneWalletApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn()) "home" else "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
//        composable("register") { RegisterScreen(navController) }
    }
}

fun isUserLoggedIn(): Boolean {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser != null
}
