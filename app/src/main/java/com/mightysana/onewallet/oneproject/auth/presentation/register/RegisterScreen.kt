package com.mightysana.onewallet.oneproject.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Main
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.components.AuthFormCard
import com.mightysana.onewallet.oneproject.auth.model.Register
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.components.ErrorSupportingText
import com.mightysana.onewallet.oneproject.components.OneButton
import com.mightysana.onewallet.oneproject.components.OneDatePicker
import com.mightysana.onewallet.oneproject.components.OneDatePickerField
import com.mightysana.onewallet.oneproject.components.OneDropdownMenuField
import com.mightysana.onewallet.oneproject.components.OneDropdownMenuItem
import com.mightysana.onewallet.oneproject.components.OneDropdownMenuItemPair
import com.mightysana.onewallet.oneproject.components.OneIcon
import com.mightysana.onewallet.oneproject.components.LoaderScreen
import com.mightysana.onewallet.oneproject.components.OneTextField
import com.mightysana.onewallet.oneproject.model.Gender
import com.mightysana.onewallet.oneproject.model.OneIcons
import com.mightysana.onewallet.oneproject.model.OneProject
import com.mightysana.onewallet.oneproject.model.convertMillisToDate
import com.mightysana.onewallet.oneproject.model.navigateAndPopUp

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
        LoaderScreen(
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
                val showDatePicker by viewModel.datePickerVisible.collectAsState()
                val selectedDate by viewModel.birthDate.collectAsState()

                val datePickerState = rememberDatePickerState()

                AuthFormCard(
                    title = stringResource(R.string.register_title),
                    subtitle = stringResource(R.string.register_subtitle),
                    modifier = Modifier.widthIn(max = OneProject.MaxFormWidth).fillMaxWidth(0.85f),
                ) {
                    // NameTextField
                    OneTextField(
                        value = viewModel.name.collectAsState().value,
                        onValueChange = { viewModel.setName(it) },
                        label = {
                            Text(
                                text = stringResource(R.string.name_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = { OneIcon(OneIcons.Name) },
                        supportingText = {
                            ErrorSupportingText(viewModel.nameError.collectAsState().value)
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // GenderDropdownField
                    val items = listOf(
                        OneDropdownMenuItemPair(
                            OneDropdownMenuItem(Gender.MALE),
                            stringResource(R.string.male)
                        ),
                        OneDropdownMenuItemPair(
                            OneDropdownMenuItem(Gender.FEMALE),
                            stringResource(R.string.female)
                        ),
                        OneDropdownMenuItemPair(
                            OneDropdownMenuItem(Gender.SECRET),
                            stringResource(R.string.prefer_not_to_say)
                        ),
                    )
                    val gender by viewModel.gender.collectAsState()
                    OneDropdownMenuField(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = viewModel.genderExpanded.collectAsState().value,
                        items = items,
                        onExpandedChange = { viewModel.toggleGenderExpanded() },
                        onDismissRequest = { viewModel.collapseGender() },
                        selectedItem = OneDropdownMenuItem(gender),
                        leadingIcon = {
                            OneIcon(
                                if(gender == Gender.FEMALE) OneIcons.Female
                                else if(gender == Gender.MALE) OneIcons.Male
                                else if(gender == Gender.SECRET) OneIcons.Secret
                                else OneIcons.Gender
                            )
                        },
                        onItemSelected = { viewModel.setGender(it.item as Gender?) },
                        label = {
                            Text(
                                text = stringResource(R.string.gender_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        legendText = stringResource(R.string.select_gender),
                        supportingText = {
                            ErrorSupportingText(viewModel.genderError.collectAsState().value)
                        }
                    )

                    // DatePicker
                    OneDatePickerField(
                        value = (if(selectedDate == 0L) System.currentTimeMillis() else selectedDate).convertMillisToDate(),
                        label = {
                            Text(
                                text = stringResource(R.string.birth_date_label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        supportingText = {
                            ErrorSupportingText(viewModel.birthDateError.collectAsState().value)
                        },
                        onExpand = { viewModel.showDatePicker() }
                    )

                    // RegisterButton
                    OneButton(
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {
                            viewModel.validateRegisterForm(context) {
                                viewModel.register {
                                    navController.navigateAndPopUp(Main, Register)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.register_title),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // NavigateToLoginButton
                    TextButton(
                        onClick = {
                            viewModel.onSignOut {
                                navController.navigateAndPopUp(SignIn, Register)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.back_to_sign_in))
                    }
                }

                // DatePickerDialog
                AnimatedVisibility(showDatePicker) {
                    OneDatePicker(
                        confirmButtonText = {
                            Text(text = stringResource(R.string.save))
                        },
                        onConfirm = { viewModel.setBirthDate(datePickerState.selectedDateMillis!!) },
                        dismissButtonText = {
                            Text(text = stringResource(R.string.cancel))
                        },
                        onDismiss = { viewModel.dismissDatePicker() },
                        datePickerState = datePickerState
                    )
                }
            }
        }
    }
}