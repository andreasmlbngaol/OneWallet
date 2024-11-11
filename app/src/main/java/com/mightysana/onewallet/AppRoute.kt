package com.mightysana.onewallet

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mightysana.onewallet.main.presentation.main.MainScreen
import com.mightysana.onewallet.oneproject.auth.model.authGraph
import com.mightysana.onewallet.oneproject.components.NetworkCheckerScreen
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.model.OneAppState
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Profile

@Composable
fun MyAppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsState()
    val authStartDestination by viewModel.authStartDestination.collectAsState()
    val appState by viewModel.appState.collectAsState()
    val networkAvailable by viewModel.isNetworkAvailable.collectAsState()
    NetworkCheckerScreen(!networkAvailable) {
        OneScreen(appState) {
            if  (appState == OneAppState.OKAY)
                NavHost(
                    navController = navController,
                    startDestination = startDestination!!,
                    modifier = modifier
                ) {
                    authGraph(
                        navController = navController,
                        startDestination = authStartDestination!!
                    )

                    composable<Main> {
                        Log.d("AppRoute", "Main...")
                        MainScreen(appController = navController)
                    }

                    composable<Profile> {
                        MainScreen(appController = navController)
                    }
                }
        }
    }
}