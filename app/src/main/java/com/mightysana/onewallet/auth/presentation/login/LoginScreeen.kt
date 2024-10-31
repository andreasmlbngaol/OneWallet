package com.mightysana.onewallet.auth.presentation.login

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
import com.mightysana.onewallet.Register
import com.mightysana.onewallet.components.Preview

@Composable
fun LoginScreen(
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
            LoginForm(
                modifier = Modifier.fillMaxWidth(0.85f),
                onNavigateToRegister = { navController.navigate(Register) }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    Preview {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}