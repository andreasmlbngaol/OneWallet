package com.mightysana.onewallet.auth.presentation.sign_up

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.R
import com.mightysana.onewallet.auth.SignIn
import com.mightysana.onewallet.auth.SignUp
import com.mightysana.onewallet.auth.presentation.components.AuthForm
import com.mightysana.onewallet.auth.presentation.components.AuthOptions
import com.mightysana.onewallet.auth.presentation.components.SignUpFormContent
import com.mightysana.onewallet.components.Preview
import com.mightysana.onewallet.navigateAndPopUp

@Composable
fun SignUpScreen(
    iconLauncher: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
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
            val context = LocalContext.current
            AuthForm(
                formImage = iconLauncher,
                title = stringResource(R.string.register_title),
                mainContent = {
                    SignUpFormContent(
                        email = viewModel.email.collectAsState().value,
                        password = viewModel.password.collectAsState().value,
                        confirmPassword = viewModel.confirmPassword.collectAsState().value,
                        passwordVisibility = viewModel.passwordVisibility.collectAsState().value,
                        confirmPasswordVisibility = viewModel.confirmPasswordVisibility.collectAsState().value,
                        onEmailChange = { viewModel.setEmail(it) },
                        onPasswordChange = { viewModel.setPassword(it) } ,
                        onConfirmPasswordChange = { viewModel.setConfirmPassword(it) },
                        onPasswordVisibilityChange = { viewModel.togglePasswordVisibility() },
                        onConfirmPasswordVisibilityChange = { viewModel.toggleConfirmPasswordVisibility() }
                    )
                },
                onMainButtonClick = {
                    viewModel.validateForm(
                        context = context,
                        onSuccess = {
                            viewModel.onSignUpWithEmailAndPassword { navController.navigateAndPopUp(Home, SignUp) }
                        }
                    )
                },
                secondaryContent = {
                    AuthOptions(
                        horizontalDividerText = stringResource(R.string.or_continue_with),
                        onGetCredentialResponse = {
                            viewModel.onSignInWithGoogle(it) {
                                navController.navigateAndPopUp(Home, SignUp)
                            }
                        }
                    )
                },
                navButtonText = stringResource(R.string.already_have_account),
                onNavButtonClick = { navController.navigateAndPopUp(SignIn, SignUp) },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun RegisterScreenPreview() {
    Preview {
        SignUpScreen(
            iconLauncher = painterResource(R.drawable.one_wallet_logo_round),
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}