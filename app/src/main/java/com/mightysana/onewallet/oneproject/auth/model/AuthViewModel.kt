package com.mightysana.onewallet.oneproject.auth.model

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.Main
import com.mightysana.onewallet.oneproject.model.OneViewModel
import com.mightysana.onewallet.oneproject.model.clip
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AuthViewModel : OneViewModel() {
    // Declaration
    protected val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    protected val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    protected val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    protected val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    protected val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    // Assignment
    fun setEmail(newEmail: String) {
        _email.value = newEmail.lowercase()
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    protected fun emailError(message: String) {
        _emailError.value = message
    }

    protected fun passwordError(message: String) {
        _passwordError.value = message
    }

    // Reset Values
    private fun resetEmailError() {
        _emailError.value = null
    }

    private fun resetPasswordError() {
        _passwordError.value = null
    }

    protected open fun resetErrors() {
        resetEmailError()
        resetPasswordError()
    }

    // Form Checking
    protected fun isEmailBlank(): Boolean {
        return _email.clip().isBlank()
    }

    protected fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_email.clip()).matches()
    }

    protected fun isPasswordBlank(): Boolean {
        Log.d("AuthViewModel", "isPasswordBlank: ${_password.clip().isBlank()}")
        return _password.clip().isBlank()
    }

    protected fun isPasswordValid(): Boolean {
        return Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$").matches(_password.clip())
    }

    // User Authentication Checking
    suspend fun checkUserRegistrationStatus(destinationRoute: (Any) -> Unit) {
        appLoading()
        Log.d("AuthViewModel", "checkUserRegistrationStatus: ${appState.value}")
        destinationRoute(if (oneRepository.isUserRegistered(accountService.currentUser!!.uid)) Main else Register)
    }

    // Sign In With Google
    fun onSignInWithGoogle(
        credential: Credential,
        onSuccess: (Any) -> Unit
    ) {
        viewModelScope.launch {
            appLoading()
            try {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    accountService.signInWithGoogle(googleIdTokenCredential.idToken)
                }
                while (accountService.currentUser == null) {
                    delay(500L)
                }
                checkUserRegistrationStatus { destination ->
                    onSuccess(destination)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "onSignInWithGoogle: $e")
                e.printStackTrace()
            }
        }
    }
}