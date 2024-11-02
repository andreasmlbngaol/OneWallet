package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.oneproject.auth.model.AuthService
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authService: AuthService
) : OneViewModel() {
    private val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun onSignInWithEmailAndPassword(
        onFailure: (Int) -> Unit,
        onEmailVerified: () -> Unit,
        onEmailNotVerified: () -> Unit
    ) {
        loadScope {
            try {
                authService.signInWithEmailAndPassword(_email.value.trim(), _password.value.trim())
                if (authService.isEmailVerified()) onEmailVerified() else onEmailNotVerified()
            } catch (e: Exception) {
                val errorCode = if(_email.value.trim().isEmpty() || _password.value.trim().isEmpty()) 1 else 2
                onFailure(errorCode)
            }
        }
    }

    fun onSignInWithGoogle(
        credential: Credential,
        onSuccess: () -> Unit
    ) {
        launchCatching {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                authService.signInWithGoogle(googleIdTokenCredential.idToken)
            }
            onSuccess()
        }
    }
}