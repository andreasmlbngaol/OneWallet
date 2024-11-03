package com.mightysana.onewallet.oneproject.auth.model

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.model.impl.AuthServiceImpl
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(
) : OneViewModel() {
    val authService: AuthService = AuthServiceImpl()

    fun onSignInWithGoogle(
        credential: Credential,
        onFailure: () -> Unit,
        onSuccess: (Any) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    authService.signInWithGoogle(googleIdTokenCredential.idToken)
                }
                while (authService.currentUser == null) {
                    delay(100L)  // Cek setiap 100ms sampai currentUser tidak null
                }

                checkUserRegistrationStatus { destination ->
                    onSuccess(destination)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "onSignInWithGoogle: $e")
                onFailure()
            }
        }
    }

    fun checkUserRegistrationStatus(destinationRoute: (Any) -> Unit) {
        viewModelScope.launch {
            destinationRoute(if (oneRepository.isUserRegistered(authService.currentUser!!.uid)) Home else Register)
        }
    }

}