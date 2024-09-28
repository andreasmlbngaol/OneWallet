package com.mightysana.onewallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mightysana.onewallet.screens.home.HomeScreen
import com.mightysana.onewallet.screens.login.LoginScreen
import com.mightysana.onewallet.screens.register.RegisterScreen
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn()) "home" else "login"
        ) {
            composable("login") { LoginScreen(navController) }
            composable("home") { HomeScreen(navController) }
            composable("register") { RegisterScreen(navController) }
        }
    }
}

fun isUserLoggedIn(): Boolean {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser != null
}
