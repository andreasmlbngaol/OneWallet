package com.mightysana.onewallet.oneproject.auth.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.R
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.presentation.components.AuthForm
import com.mightysana.onewallet.oneproject.auth.presentation.components.MAX_FORM_WIDTH
import com.mightysana.onewallet.oneproject.auth.presentation.components.RegisterFormContent
import com.mightysana.onewallet.oneproject.components.OneDropdownMenuDefault
import com.mightysana.onewallet.oneproject.components.OneOutlinedButton
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.components.OneTextFieldDefault
import com.mightysana.onewallet.oneproject.model.Gender

@OptIn(ExperimentalMaterial3Api::class)
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
                val datePickerState = rememberDatePickerState()

                AuthForm(
                    title = stringResource(R.string.register_title),
                    mainContent = {
                        RegisterFormContent(
                            name = OneTextFieldDefault(
                                value = viewModel.name.collectAsState().value,
                                onValueChange = { viewModel.setName(it) },
                                labelText = stringResource(R.string.name_label),
                                errorMessage = viewModel.nameError.collectAsState().value
                            ),
                            gender = OneDropdownMenuDefault(
                                expanded = viewModel.genderExpanded.collectAsState().value,
                                onExpandedChange = {
                                    viewModel.setGenderExpanded(it)
                                    Log.d("onExpandedChange", "$it")
                                },
                                selectedItem = viewModel.gender.collectAsState().value,
                                labelText = stringResource(R.string.gender_label),
                                items = mapOf<Any?, String>(
                                    null to stringResource(R.string.select_gender),
                                    Gender.MALE to stringResource(R.string.male),
                                    Gender.FEMALE to stringResource(R.string.female),
                                    Gender.SECRET to stringResource(R.string.prefer_not_to_say),
                                ),
                                onItemSelected = {
                                    viewModel.setGender(it as Gender?)
                                    viewModel.setGenderExpanded(false)
                                },
                                onDismissRequest = { viewModel.setGenderExpanded(false) },
                                errorMessage = viewModel.genderError.collectAsState().value
                            ),
                            birthDate = OneTextFieldDefault(
                                value = viewModel.birthDate.collectAsState().value,
                                onValueChange = { viewModel.setBirthDate(it) },
                                labelText = stringResource(R.string.birth_date_label),
                                errorMessage = viewModel.birthDateError.collectAsState().value
                            ),
                            onBirthdateTrailingIconClick = {
                                viewModel.setDatePickerVisible(true)
                            },
                            datePickerState = datePickerState
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
                    modifier = Modifier.widthIn(max = MAX_FORM_WIDTH.dp).fillMaxWidth(0.85f)
                )
            }
        }
    }
}