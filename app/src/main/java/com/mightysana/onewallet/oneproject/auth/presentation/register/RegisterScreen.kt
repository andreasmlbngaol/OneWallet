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
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthForm
import com.mightysana.onewallet.oneproject.auth.presentation.components.RegisterFormContent
import com.mightysana.onewallet.oneproject.components.OneOutlinedButton
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.components.OneTextFieldDefault

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel(),
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
                            name = OneTextFieldDefault(
                                value = viewModel.name.collectAsState().value,
                                onValueChange = { viewModel.setName(it) },
                                label = stringResource(R.string.name_label),
                                errorMessage = viewModel.nameError.collectAsState().value
                            ),
                            gender = OneTextFieldDefault(
                                value = viewModel.gender.collectAsState().value,
                                onValueChange = { viewModel.setGender(it) },
                                label = stringResource(R.string.gender_label),
                                errorMessage = viewModel.genderError.collectAsState().value
                            ),
                            birthDate = OneTextFieldDefault(
                                value = viewModel.birthDate.collectAsState().value,
                                onValueChange = { viewModel.setBirthDate(it) },
                                label = stringResource(R.string.birth_date_label),
                                errorMessage = viewModel.birthDateError.collectAsState().value
                            )
                        )
                    },
                    onMainButtonClick = {
                        viewModel.validateForm(context) {
                            viewModel.register {
                                navController.navigateAndPopUp(Home, Register)
                            }
                        }
                    },
                    secondaryContent ={
                        OneOutlinedButton(
                            onClick = {
                                viewModel.onSignOut {
                                    navController.navigateAndPopUp(SignIn, Register)
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