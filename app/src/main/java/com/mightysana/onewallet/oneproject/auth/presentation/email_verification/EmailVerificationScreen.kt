package com.mightysana.onewallet.oneproject.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.mightysana.onewallet.oneproject.auth.model.EmailVerification
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.model.OneIcons
import com.mightysana.onewallet.oneproject.model.censoredEmail
import com.mightysana.onewallet.oneproject.model.navigateAndPopUp
import kotlinx.coroutines.coroutineScope

@Composable
fun EmailVerificationScreen(
    navController: NavHostController,
    viewModel: EmailVerificationViewModel = hiltViewModel()
) {
    viewModel.checkEmailVerification {
        coroutineScope {
            viewModel.checkUserRegistrationStatus { destination ->
                navController.navigateAndPopUp(destination, EmailVerification)
            }
        }
    }

    val context = LocalContext.current

    Scaffold { innerPadding ->
        OneScreen(
            viewModel.appState.collectAsState().value
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Icon(
                        imageVector = OneIcons.EmailVerification,
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .aspectRatio(1f)
                    )
                    Text(
                        text = stringResource(R.string.email_verification_message, viewModel.authUserEmail!!.censoredEmail()),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { viewModel.openEmailApp(context)}
                        ) {
                            Text(text = stringResource(R.string.open_email_app))
                        }

                        TextButton(
                            onClick = {
                                viewModel.onSignOut {
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