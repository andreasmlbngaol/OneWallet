package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import android.content.Context
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : AuthViewModel() {
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
        onEmailVerified: (Any) -> Unit,
        onEmailNotVerified: () -> Unit
    ) {
        loadScope {
            try {
                authService.signInWithEmailAndPassword(_email.value.trim(), _password.value.trim())
                if (!authService.isEmailVerified()) onEmailNotVerified() else checkUserRegistrationStatus { onEmailVerified(it) }
            } catch (e: Exception) {
                val errorCode = if(_email.value.trim().isEmpty() || _password.value.trim().isEmpty()) 1 else 2
                onFailure(errorCode)
            }
        }
    }

    private fun resetError() {
        _emailError.value = Pair(false, "")
        _passwordError.value = Pair(false, "")
    }

    fun validateForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetError()
        val validationState = when {
            isEmailBlank() -> SignInFormValidationResult.EmailBlank
            !isEmailValid() -> SignInFormValidationResult.EmailInvalid
            isPasswordBlank() -> SignInFormValidationResult.PasswordBlank
            else -> SignInFormValidationResult.Valid
        }

        if(validationState is SignInFormValidationResult.Valid) {
            onSuccess()
        } else {
            when(validationState) {
                is SignInFormValidationResult.EmailBlank -> emailError(context.getString(
                    R.string.email_is_blank))
                is SignInFormValidationResult.EmailInvalid -> emailError(context.getString(
                    R.string.email_is_not_valid))
                else -> passwordError(context.getString(
                    R.string.password_is_blank))
            }
        }
    }

}

sealed class SignInFormValidationResult {
    data object Valid : SignInFormValidationResult()
    data object EmailBlank : SignInFormValidationResult()
    data object EmailInvalid : SignInFormValidationResult()
    data object PasswordBlank : SignInFormValidationResult()
}