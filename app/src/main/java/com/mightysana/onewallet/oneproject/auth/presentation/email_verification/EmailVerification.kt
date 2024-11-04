package com.mightysana.onewallet.oneproject.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.R
import com.mightysana.onewallet.navigateAndPopUp
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.components.OneOutlinedButton
import com.mightysana.onewallet.oneproject.components.OneScreen

@Composable
fun EmailVerification(
    navController: NavHostController,
    viewModel: EmailVerificationViewModel = hiltViewModel()
) {
    viewModel.checkEmailVerification {
        viewModel.checkUserRegistrationStatus { destination ->
            navController.navigateAndPopUp(destination, EmailVerification)
        }
    }

    val context = LocalContext.current

    Scaffold { innerPadding ->
        OneScreen(viewModel.appState.collectAsState().value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.width(100.dp).aspectRatio(1f)
                    )
                    Text(
                        text = stringResource(R.string.email_verification_message, viewModel.authUserEmail!!.censoredEmail()),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OneOutlinedButton(
                            onClick = { viewModel.openEmailApp(context)}
                        ) {
                            Text(text = stringResource(R.string.open_email_app))
                        }

                        TextButton(
                            onClick = {
                                viewModel.signOut {
                                    navController.navigateAndPopUp(SignIn, EmailVerification)
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.back_to_sign_in))
                        }

                    }
                }
            }
        }
    }
}