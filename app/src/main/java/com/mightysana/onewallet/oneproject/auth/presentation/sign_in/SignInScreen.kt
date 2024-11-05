package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.R
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.model.EmailVerification
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.auth.model.SignUp
import com.mightysana.onewallet.oneproject.auth.components.AuthFormCard
import com.mightysana.onewallet.oneproject.auth.components.AuthOtherOptions
import com.mightysana.onewallet.oneproject.components.ErrorSupportingText
import com.mightysana.onewallet.oneproject.components.OneIcon
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.components.OneTextField
import com.mightysana.onewallet.oneproject.model.MAX_FORM_WIDTH
import com.mightysana.onewallet.oneproject.model.toast

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

                AuthFormCard(
                    formImage = iconLauncher,
                    title = stringResource(R.string.sign_in_title),
                    modifier = Modifier.widthIn(max = MAX_FORM_WIDTH.dp).fillMaxWidth(0.85f),
                ) {
                    // EmailTextField
                    OneTextField(
                        value = viewModel.email.collectAsState().value,
                        onValueChange = { viewModel.setEmail(it) },
                        label = {
                            Text(
                                text = stringResource(R.string.email_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = { OneIcon(Icons.Default.Email) },
                        supportingText = {
                            ErrorSupportingText(viewModel.emailError.collectAsState().value)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                    // PasswordTextField
                    val visible by viewModel.visible.collectAsState()
                    OneTextField(
                        value = viewModel.password.collectAsState().value,
                        onValueChange = { viewModel.setPassword(it) },
                        label = {
                            Text(
                                text = stringResource(R.string.password_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = { OneIcon(Icons.Default.Key) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.togglePasswordVisibility() }
                            ) {
                                OneIcon(
                                    if(!visible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                )
                            }
                        },
                        supportingText = {
                            ErrorSupportingText(viewModel.passwordError.collectAsState().value)
                        },
                        visualTransformation = if(!visible) PasswordVisualTransformation() else VisualTransformation.None,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // LoginButton
                    Button(
                        onClick = {
                            viewModel.validateSignInForm(context) {
                                viewModel.onSignInWithEmailAndPassword(
                                    onFailure = { context.toast(if (it == 1) R.string.form_blank else R.string.login_failed) },
                                    onEmailVerified = { destination ->
                                        navController.navigateAndPopUp(destination, SignIn)
                                    },
                                    onEmailNotVerified = {
                                        navController.navigateAndPopUp(EmailVerification, SignIn)
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in_title),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // OtherAuthOptions
                    AuthOtherOptions(
                        horizontalDividerText = stringResource(R.string.or_continue_with),
                        onLoad = { viewModel.triggerAppLoading() },
                        onOkay = { viewModel.triggerAppOkay() },
                    ) { credential ->
                        viewModel.onSignInWithGoogle(credential) { destination ->
                            navController.navigateAndPopUp(destination, SignIn)
                        }
                    }

                    // NavToRegisterButton
                    TextButton(
                        onClick = { navController.navigate(SignUp) }
                    ) {
                        Text(
                            text = stringResource(R.string.dont_have_an_account),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}