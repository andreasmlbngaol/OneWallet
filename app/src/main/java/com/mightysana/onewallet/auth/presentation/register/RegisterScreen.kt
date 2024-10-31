package com.mightysana.onewallet.auth.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mightysana.onewallet.Login
import com.mightysana.onewallet.Register
import com.mightysana.onewallet.components.Preview
import com.mightysana.onewallet.navigateAndPopUp

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RegisterForm(
                modifier = Modifier.fillMaxWidth(0.85f),
                onNavigateToLogin = { navController.navigateAndPopUp(Login, Register) }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun RegisterScreenPreview() {
    Preview {
        RegisterScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}