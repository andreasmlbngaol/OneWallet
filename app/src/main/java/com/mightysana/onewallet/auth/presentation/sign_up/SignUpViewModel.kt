package com.mightysana.onewallet.auth.presentation.sign_up

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.R
import com.mightysana.onewallet.auth.functions.toast
import com.mightysana.onewallet.auth.model.AuthService
import com.mightysana.onewallet.auth.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService
): OneViewModel() {
    private val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    private val _confirmPasswordVisibility = MutableStateFlow(false)
    val confirmPasswordVisibility: StateFlow<Boolean> = _confirmPasswordVisibility

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun setConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun toggleConfirmPasswordVisibility() {
        _confirmPasswordVisibility.value = !_confirmPasswordVisibility.value
    }

    private fun isEmailBlank(): Boolean {
        return _email.value.isBlank()
    }

    private fun isPasswordBlank(): Boolean {
        return _password.value.isBlank()
    }

    private fun isConfirmPasswordBlank(): Boolean {
        return _confirmPassword.value.isBlank()
    }

    private fun isPasswordAndConfirmPasswordSame(): Boolean {
        return _password.value == _confirmPassword.value
    }

    private fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    private fun isPasswordValid(): Boolean {
        return Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$").matches(_password.value)
    }

    fun validateForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        val validationState = when {
            isEmailBlank() -> FormValidationState.Invalid(getString(context, R.string.email_is_blank))
            !isEmailValid() -> FormValidationState.Invalid(getString(context, R.string.email_is_not_valid))
            isPasswordBlank() -> FormValidationState.Invalid(getString(context, R.string.password_is_blank))
            !isPasswordValid() -> FormValidationState.Invalid(getString(context, R.string.password_is_not_valid))
            isConfirmPasswordBlank() -> FormValidationState.Invalid(getString(context, R.string.confirm_password_is_blank))
            !isPasswordAndConfirmPasswordSame() -> FormValidationState.Invalid(getString(context, R.string.password_and_confirm_password_not_same))
            else -> FormValidationState.Valid
        }

        val message = if(validationState is FormValidationState.Valid) getString(context, R.string.sign_up_success)
        else (validationState as FormValidationState.Invalid).message

        context.toast(message)

        if(validationState is FormValidationState.Valid) onSuccess()
    }

    fun onSignUpWithEmailAndPassword(onSuccess: () -> Unit ) {
        launchCatching {
            authService.signUpWithEmailAndPassword(_email.value.trim(), _password.value.trim())
            onSuccess()
        }
    }

    fun onSignInWithGoogle(
        credential: Credential,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                authService.signInWithGoogle(googleIdTokenCredential.idToken)
            }
            onSuccess()
        }
    }
}

sealed class FormValidationState {
    data object Valid : FormValidationState()
    data class Invalid(val message: String) : FormValidationState()
}