package com.mightysana.onewallet.oneproject.auth.presentation.sign_up

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.components.AuthFormCard
import com.mightysana.onewallet.oneproject.auth.components.AuthOptions
import com.mightysana.onewallet.oneproject.auth.model.EmailVerification
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.auth.model.SignUp
import com.mightysana.onewallet.oneproject.components.ErrorSupportingText
import com.mightysana.onewallet.oneproject.components.OneButton
import com.mightysana.onewallet.oneproject.components.OneIcon
import com.mightysana.onewallet.oneproject.components.LoaderScreen
import com.mightysana.onewallet.oneproject.components.OneTextField
import com.mightysana.onewallet.oneproject.model.OneIcons
import com.mightysana.onewallet.oneproject.model.OneProject
import com.mightysana.onewallet.oneproject.model.navigateAndPopUp
import com.mightysana.onewallet.oneproject.model.toast

@Composable
fun SignUpScreen(
    iconLauncher: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        LoaderScreen(
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
                    title = stringResource(R.string.sign_up_title),
                    modifier = Modifier.widthIn(max = OneProject.MaxFormWidth).fillMaxWidth(0.85f),
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
                        leadingIcon = { OneIcon(OneIcons.Email) },
                        supportingText = {
                            ErrorSupportingText(viewModel.emailError.collectAsState().value)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )


                    // PasswordTextField
                    val passwordVisible by viewModel.passwordVisible.collectAsState()
                    OneTextField(
                        value = viewModel.password.collectAsState().value,
                        onValueChange = { viewModel.setPassword(it) },
                        label = {
                            Text(
                                text = stringResource(R.string.password_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = { OneIcon(OneIcons.Password) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.togglePasswordVisibility() }
                            ) {
                                OneIcon(
                                    if(!passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                )
                            }
                        },
                        supportingText = {
                            ErrorSupportingText(viewModel.passwordError.collectAsState().value)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if(!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // ConfirmPasswordTextField
                    val confirmPasswordVisible by viewModel.confirmPasswordVisible.collectAsState()
                    OneTextField(
                        value = viewModel.confirmPassword.collectAsState().value,
                        onValueChange = { viewModel.setConfirmPassword(it) },
                        label = {
                            Text(
                                text = stringResource(R.string.confirm_password_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = { OneIcon(OneIcons.ConfirmPassword) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.toggleConfirmPasswordVisibility() }
                            ) {
                                OneIcon(
                                    if(!confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                )
                            }
                        },
                        supportingText = {
                            ErrorSupportingText(viewModel.confirmPasswordError.collectAsState().value)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                        visualTransformation = if(!confirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )


                    // LoginButton
                    OneButton(
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {
                            viewModel.validateSignUpForm(context) {
                                viewModel.onSignUpWithEmailAndPassword(
                                    onFailure = { context.toast(R.string.email_already_used) },
                                ) {
                                    navController.navigateAndPopUp(EmailVerification, SignUp)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up_title),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // OtherAuthOptions
                    AuthOptions(
                        horizontalDividerText = stringResource(R.string.or_continue_with),
                        onLoad = { viewModel.appLoading() },
                        onOkay = { viewModel.appOkay() },
                    ) { credential ->
                        viewModel.onSignInWithGoogle(credential) { destination ->
                            navController.navigateAndPopUp(destination, SignIn)
                        }
                    }

                    // NavToRegisterButton
                    TextButton(
                        onClick = { navController.navigateAndPopUp(SignIn, SignUp) }
                    ) {
                        Text(
                            text = stringResource(R.string.already_have_account),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
//    Scaffold(
//        modifier = modifier
//    ) { innerPadding ->
//        OneScreen(
//            state = viewModel.appState.collectAsState().value,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//            ) {
//                val context = LocalContext.current
//                AuthFormCard(
//                    formImage = iconLauncher,
//                    title = stringResource(R.string.sign_up_title),
//                    content = { },
////                        SignUpFormContent(
////                            email = OneTextFieldDefault(
////                                value = viewModel.email.collectAsState().value,
////                                onValueChange = { viewModel.setEmail(it) },
////                                labelText = context.getString(R.string.email_label),
////                                errorMessage = viewModel.emailError.collectAsState().value
////                            ),
////                            password = OneTextFieldDefault(
////                                value = viewModel.password.collectAsState().value,
////                                onValueChange = { viewModel.setPassword(it) },
////                                labelText = context.getString(R.string.password_label),
////                                errorMessage = viewModel.passwordError.collectAsState().value
////                            ),
////                            confirmPassword = OneTextFieldDefault(
////                                value = viewModel.confirmPassword.collectAsState().value,
////                                onValueChange = { viewModel.setConfirmPassword(it) },
////                                labelText = context.getString(R.string.confirm_password_label),
////                                errorMessage = viewModel.confirmPasswordError.collectAsState().value
////                            ),
////                            passwordVisibility = viewModel.passwordVisibility.collectAsState().value,
////                            confirmPasswordVisibility = viewModel.confirmPasswordVisibility.collectAsState().value,
////                            onPasswordVisibilityChange = { viewModel.togglePasswordVisibility() },
////                            onConfirmPasswordVisibilityChange = { viewModel.toggleConfirmPasswordVisibility() },
////                        )
////                    },
////                    onMainButtonClick = {
////                        viewModel.validateForm(
////                            context = context,
////                            onSuccess = {
////                                viewModel.onSignUpWithEmailAndPassword(
////                                    {
////                                        context.toast(context.getString(R.string.email_already_used))
////                                    }
////                                ) {
////                                    navController.navigateAndPopUp(EmailVerification, SignUp)
////                                }
////                            }
////                        )
////                    },
////                    secondaryContent = {
////                        AuthOptions(
////                            horizontalDividerText = stringResource(R.string.or_continue_with),
////                            onLoad = { viewModel.appLoading() },
////                            onOkay = { viewModel.appOkay() },
////                            onGetCredentialResponse = { credential ->
////                                viewModel.onSignInWithGoogle(
////                                    credential = credential,
////                                    onFailure = {
////                                        context.toast(R.string.email_already_used)
////                                    }
////                                ) { destination ->
////                                    navController.navigateAndPopUp(destination, SignUp)
////                                }
////                            }
////                        )
////                    },
////                    navButtonText = stringResource(R.string.already_have_account),
////                    onNavButtonClick = { navController.navigateAndPopUp(SignIn, SignUp) },
//                    modifier = Modifier.widthIn(max = MAX_FORM_WIDTH.dp).fillMaxWidth(0.85f)
//                )
//            }
//        }
//    }
//}