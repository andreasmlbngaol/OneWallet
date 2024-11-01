package com.mightysana.onewallet.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.R
import com.mightysana.onewallet.Register
import com.mightysana.onewallet.auth.presentation.components.AuthForm
import com.mightysana.onewallet.auth.presentation.components.AuthOptions
import com.mightysana.onewallet.auth.presentation.components.LoginFormContent
import com.mightysana.onewallet.components.Preview

@Composable
fun LoginScreen(
    iconLauncher: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
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
            // Later in ViewModel
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()
            val visibility by viewModel.passwordVisibility.collectAsState()


            AuthForm(
                formImage = iconLauncher,
                title = stringResource(R.string.login_title),
                mainContent = {
                    LoginFormContent(
                        email = email,
                        password = password,
                        visibility = visibility,
                        onEmailChange = { viewModel.setEmail(it) },
                        onPasswordChange = { viewModel.setPassword(it) } ,
                        onVisibilityChange = { viewModel.setPasswordVisibility(it) }
                    )
                },
                onMainButtonClick = { navController.navigate(Home) },
                secondaryContent = {
                    AuthOptions(
                        horizontalDividerText = stringResource(R.string.or_continue_with),
                        onSignInWithGoogle = { }
                    )
                },
                navButtonText = stringResource(R.string.dont_have_an_account),
                onNavButtonClick = { navController.navigate(Register) },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
//            LoginForm(
//                formImage = iconLauncher,
//                modifier = Modifier.fillMaxWidth(0.85f),
//                onNavigateToRegister = { navController.navigate(Register) }
//            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    Preview {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            iconLauncher = painterResource(R.drawable.one_wallet_logo_round),
            navController = rememberNavController()
        )
    }
}