package com.mightysana.onewallet.oneproject.auth.model

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.oneproject.auth.model.service.AuthService
import com.mightysana.onewallet.oneproject.auth.model.service.AuthServiceImpl
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor() : OneViewModel() {
    protected val authService: AuthService = AuthServiceImpl()

    protected val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    protected val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    protected val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    protected val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    protected fun resetEmailError() {
        _emailError.value = null
    }

    protected fun resetPasswordError() {
        _passwordError.value = null
    }

    protected fun resetErrors() {
        resetEmailError()
        resetPasswordError()
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    protected fun emailError(message: String) {
        _emailError.value = message
    }

    protected fun passwordError(message: String) {
        _passwordError.value = message
    }

    fun onSignInWithGoogle(
        credential: Credential,
        onSuccess: (Any) -> Unit
    ) {
        viewModelScope.launch {
            appLoading()
            try {
                Log.d("AuthViewModel", "onSignInWithGoogle: ${appState.value}")
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    authService.signInWithGoogle(googleIdTokenCredential.idToken)
                }
                while (authService.currentUser == null) {
                    Log.d("AuthViewModel", "onSignInWithGoogle: Waiting for currentUser")
                    delay(1000L)
                }
                Log.d("AuthViewModel", "afterDelay: ${appState.value}")
                checkUserRegistrationStatus { destination ->
                    onSuccess(destination)
                }
                Log.d("AuthViewModel", "onSignInWithGoogle: ${appState.value}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "onSignInWithGoogle: $e")
            }
        }
    }

    suspend fun checkUserRegistrationStatus(destinationRoute: (Any) -> Unit) {
        appLoading()
        Log.d("AuthViewModel", "checkUserRegistrationStatus: ${appState.value}")
        destinationRoute(if (oneRepository.isUserRegistered(authService.currentUser!!.uid)) Home else Register)
    }

    protected fun isEmailBlank(): Boolean {
        return _email.value.trim().isBlank()
    }

    protected fun isPasswordBlank(): Boolean {
        return _password.value.trim().isBlank()
    }

    protected fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value.trim()).matches()
    }
}