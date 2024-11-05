package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.R
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.SignUp
import com.mightysana.onewallet.oneproject.auth.functions.toast
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthForm
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthOptions
import com.mightysana.onewallet.oneproject.auth.presentation.components.MAX_FORM_WIDTH
import com.mightysana.onewallet.oneproject.auth.presentation.components.SignInFormContent
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.components.OneTextFieldDefault

@Composable
fun SignInScreen(
    iconLauncher: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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

                AuthForm(
                    formImage = iconLauncher,
                    title = stringResource(R.string.sign_in_title),
                    content = {
                        SignInFormContent(
                            email = OneTextFieldDefault(
                                value = viewModel.email.collectAsState().value,
                                onValueChange = { viewModel.setEmail(it) },
                                labelText = context.getString(R.string.email_label),
                                errorMessage = viewModel.emailError.collectAsState().value
                            ),
                            password = OneTextFieldDefault(
                                value = viewModel.password.collectAsState().value,
                                onValueChange = { viewModel.setPassword(it) },
                                labelText = context.getString(R.string.password_label),
                                errorMessage = viewModel.passwordError.collectAsState().value
                            ),
                            visibility = viewModel.passwordVisibility.collectAsState().value,
                            onVisibilityChange = { viewModel.togglePasswordVisibility() }
                        )
                    },
                    onMainButtonClick = {
                        viewModel.validateForm(context) {
                            viewModel.onSignInWithEmailAndPassword(
                                onFailure = { context.toast(if (it == 1) R.string.form_blank else R.string.login_failed) },
                                onEmailVerified = { destination ->
                                    navController.navigateAndPopUp(destination, SignIn)
                                },
                                onEmailNotVerified = {
                                    navController.navigateAndPopUp(
                                        EmailVerification,
                                        SignIn
                                    )
                                }
                            )
                        }
                    },
                    secondaryContent = {
                        AuthOptions(
                            horizontalDividerText = stringResource(R.string.or_continue_with),
                            onLoad = { viewModel.appLoading() },
                            onOkay = { viewModel.appOkay() },
                            onGetCredentialResponse = { credential ->
                                viewModel.onSignInWithGoogle(
                                    credential = credential,
                                    onFailure = { context.toast(R.string.email_already_used) },
                                    onSuccess = { destination ->
                                        navController.navigateAndPopUp(destination, SignIn)
                                    }
                                )
                            }
                        )
                    },
                    navButtonText = stringResource(R.string.dont_have_an_account),
                    onNavButtonClick = { navController.navigate(SignUp) },
                    modifier = Modifier.widthIn(max = MAX_FORM_WIDTH.dp).fillMaxWidth(0.85f)
                )
            }
        }
    }
}
//@PreviewLightDark
//@Composable
//private fun LoginScreenPreview() {
//    Preview {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            var email by remember { mutableStateOf("") }
//            var password by remember { mutableStateOf("") }
//            var visibility by remember { mutableStateOf(false) }
//
//            AuthForm(
//                formImage = painterResource(id = R.drawable.one_wallet_logo_round),
//                title = stringResource(R.string.login_title),
//                mainContent = {
//                    SignInFormContent(
//                        email = email,
//                        password = password,
//                        visibility = visibility,
//                        onEmailChange = { email = it },
//                        onPasswordChange = { password = it } ,
//                        onVisibilityChange = { visibility = !visibility }
//                    )
//                },
//                onMainButtonClick = {  },
//                secondaryContent = {
//                    AuthOptions(
//                        onLoad = {  },
//                        onOkay = {  },
//                        horizontalDividerText = stringResource(R.string.or_continue_with),
//                        onGetCredentialResponse = { }
//                    )
//                },
//                navButtonText = stringResource(R.string.dont_have_an_account),
//                onNavButtonClick = {  },
//                modifier = Modifier.fillMaxWidth(0.85f)
//            )
//        }
//    }
//}