package com.mightysana.onewallet.oneproject.auth.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.R
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.functions.toast
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthForm
import com.mightysana.onewallet.oneproject.auth.presentation.components.RegisterFormContent
import com.mightysana.onewallet.oneproject.auth.presentation.components.SignInFormContent
import com.mightysana.onewallet.oneproject.components.OneOutlinedButton
import com.mightysana.onewallet.oneproject.components.OneScreen

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        OneScreen(
            state = viewModel.appState.collectAsState().value,
            modifier = Modifier.padding(innerPadding)
        ) {
            val context = LocalContext.current
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AuthForm(
                    title = stringResource(R.string.register_title),
                    mainContent = {
                        RegisterFormContent(
                            name = viewModel.name.collectAsState().value,
                            username = viewModel.username.collectAsState().value,
                            gender = viewModel.gender.collectAsState().value,
                            birthDate = viewModel.birthDate.collectAsState().value,
                            onEmailChange = { viewModel.setName(it) },
                            onUsernameChange = { viewModel.setUsername(it) },
                            onGenderChange = { viewModel.setGender(it) },
                            onBirthDateChange = { viewModel.setBirthDate(it) },
                            nameError = viewModel.nameError.collectAsState().value,
                            usernameError = viewModel.usernameError.collectAsState().value,
                            genderError = viewModel.genderError.collectAsState().value,
                            birthDateError = viewModel.birthDateError.collectAsState().value
                        )
                    },
                    onMainButtonClick = {
                        viewModel.validateForm(context) {
//                            viewModel.onSignInWithEmailAndPassword(
//                                onFailure = { context.toast(if (it == 1) R.string.form_blank else R.string.login_failed) },
//                                onEmailVerified = { destination ->
//                                    navController.navigateAndPopUp(destination, SignIn)
//                                },
//                                onEmailNotVerified = {
//                                    navController.navigateAndPopUp(
//                                        EmailVerification,
//                                        SignIn
//                                    )
//                                }
//                            )
                        }
                    },
                    secondaryContent ={
                        OneOutlinedButton(
                            onClick = {
                                viewModel.onSignOut {
                                    navController.navigateAndPopUp(SignIn, Home)
                                }
                            }
                        ) {
                            Text(text = "Sign Out From Register")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }
        }
    }
}