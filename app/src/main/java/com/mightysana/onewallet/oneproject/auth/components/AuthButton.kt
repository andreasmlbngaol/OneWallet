package com.mightysana.onewallet.oneproject.auth.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.components.ImageButton
import kotlinx.coroutines.launch

@Composable
fun GoogleAuthButton(
    onLoad: () -> Unit,
    onOkay: () -> Unit,
    onGetCredentialResponse: (Credential) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    ImageButton(
        painter = painterResource(id = R.drawable.google_logo),
        onClick = {
            onLoad()
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
                    Log.d("GoogleAuthButton", "Credential: ${result.credential}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    onOkay()
                    Log.e("GoogleAuthButton", "Error getting credential: ${e.message}")
                }
            }
        },
    )
}