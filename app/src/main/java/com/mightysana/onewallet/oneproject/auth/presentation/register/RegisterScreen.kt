package com.mightysana.onewallet.oneproject.auth.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.components.OneOutlinedButton
import com.mightysana.onewallet.oneproject.components.OneScreen

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    OneScreen(viewModel.appState.collectAsState().value) {
        Scaffold { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(innerPadding)
            ) {
                OneOutlinedButton(
                    onClick = {
                        viewModel.onSignOut {
                            navController.navigateAndPopUp(SignIn, Home)
                        }
                    }
                ) {
                    Text(text = "Sign Out From Register")
                }
            }
        }
    }
}