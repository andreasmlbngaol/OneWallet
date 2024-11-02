package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.SignUp
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthForm
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthOptions
import com.mightysana.onewallet.oneproject.auth.presentation.components.SignInFormContent
import com.mightysana.onewallet.oneproject.components.Preview
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.components.OneScreen

@Composable
fun SignInScreen(
    iconLauncher: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        OneScreen(
            state = viewModel.appState.collectAsState().value,
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                val email by viewModel.email.collectAsState()
                val password by viewModel.password.collectAsState()
                val visibility by viewModel.passwordVisibility.collectAsState()


                AuthForm(
                    formImage = iconLauncher,
                    title = stringResource(R.string.login_title),
                    mainContent = {
                        SignInFormContent(
                            email = email,
                            password = password,
                            visibility = visibility,
                            onEmailChange = { viewModel.setEmail(it) },
                            onPasswordChange = { viewModel.setPassword(it) },
                            onVisibilityChange = { viewModel.togglePasswordVisibility() }
                        )
                    },
                    onMainButtonClick = {
                        viewModel.onSignInWithEmailAndPassword(
                            onEmailVerified = { navController.navigateAndPopUp(Home, SignIn) },
                            onEmailNotVerified = { navController.navigateAndPopUp(EmailVerification, SignIn) }
                        )

                    },
                    secondaryContent = {
                        AuthOptions(
                            horizontalDividerText = stringResource(R.string.or_continue_with),
                            onLoad = { viewModel.appLoading() },
                            onOkay = { viewModel.appOkay() },
                            onGetCredentialResponse = {
                                viewModel.onSignInWithGoogle(it) {
                                    navController.navigateAndPopUp(Home, SignIn)
                                }
                            }
                        )
                    },
                    navButtonText = stringResource(R.string.dont_have_an_account),
                    onNavButtonClick = { navController.navigate(SignUp) },
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }
        }
    }
}
@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    Preview {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var visibility by remember { mutableStateOf(false) }

            AuthForm(
                formImage = painterResource(id = R.drawable.one_wallet_logo_round),
                title = stringResource(R.string.login_title),
                mainContent = {
                    SignInFormContent(
                        email = email,
                        password = password,
                        visibility = visibility,
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it } ,
                        onVisibilityChange = { visibility = !visibility }
                    )
                },
                onMainButtonClick = {  },
                secondaryContent = {
                    AuthOptions(
                        onLoad = {  },
                        onOkay = {  },
                        horizontalDividerText = stringResource(R.string.or_continue_with),
                        onGetCredentialResponse = { }
                    )
                },
                navButtonText = stringResource(R.string.dont_have_an_account),
                onNavButtonClick = {  },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        }
    }
}