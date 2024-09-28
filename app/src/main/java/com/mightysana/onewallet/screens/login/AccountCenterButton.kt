package com.mightysana.onewallet.screens.login

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.mightysana.onewallet.R
import kotlinx.coroutines.launch

@Composable
fun AuthenticationButton(
    buttonText: Int,
    onGetCredentialResponse: (Credential) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    Button(
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = {
            Log.d("AuthenticationButton", "Button clicked")
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )
                    onGetCredentialResponse(result.credential)
                } catch (e: Exception) {
                    Log.d("AuthenticationButton", "Error: ${e.message.orEmpty()}")
                }
            }
        }
    ) {
        Text(text = context.getString(buttonText))
    }
}