package com.mightysana.onewallet.oneproject.auth.presentation.sign_up

import android.content.Context
import android.util.Log
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import com.mightysana.onewallet.oneproject.model.SignUpFormValidationResult
import com.mightysana.onewallet.oneproject.model.clip
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext context: Context
): AuthViewModel(context) {
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _confirmPasswordVisible = MutableStateFlow(false)
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError = _confirmPasswordError.asStateFlow()

    fun setConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun toggleConfirmPasswordVisibility() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    private fun confirmPasswordError(message: String) {
        _confirmPasswordError.value = message
    }

    private fun resetConfirmPasswordError() {
        _confirmPasswordError.value = null
    }

    private fun isConfirmPasswordBlank(): Boolean {
        return _confirmPassword.clip().isBlank()
    }

    private fun isPasswordAndConfirmPasswordSame(): Boolean {
        return _password.clip() == _confirmPassword.clip()
    }

    override fun resetErrors() {
        super.resetErrors()
        resetConfirmPasswordError()
    }

    fun validateSignUpForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetErrors()
        val validationState = when {
            isEmailBlank() -> SignUpFormValidationResult.EmailBlank
            !isEmailValid() -> SignUpFormValidationResult.EmailNotValid
            isPasswordBlank() -> SignUpFormValidationResult.PasswordBlank
            !isPasswordValid() -> SignUpFormValidationResult.PasswordNotValid
            isConfirmPasswordBlank() -> SignUpFormValidationResult.ConfirmPasswordBlank
            !isPasswordAndConfirmPasswordSame() -> SignUpFormValidationResult.PasswordAndConfirmPasswordNotSame
            else -> SignUpFormValidationResult.Valid
        }

        if(validationState is SignUpFormValidationResult.Valid) {
            onSuccess()
        } else {
            when(validationState) {
                is SignUpFormValidationResult.EmailBlank -> emailError(context.getString(R.string.email_is_blank))
                is SignUpFormValidationResult.EmailNotValid -> emailError(context.getString(R.string.email_is_not_valid))
                is SignUpFormValidationResult.PasswordBlank -> passwordError(context.getString(R.string.password_is_blank))
                is SignUpFormValidationResult.PasswordNotValid -> passwordError(context.getString(R.string.password_is_not_valid))
                is SignUpFormValidationResult.ConfirmPasswordBlank -> confirmPasswordError(context.getString(R.string.confirm_password_is_blank))
                else -> confirmPasswordError(context.getString(R.string.password_and_confirm_password_not_same))
            }
        }
    }

    fun onSignUpWithEmailAndPassword(
        onFailure: () -> Unit,
        onSuccess: () -> Unit
    ) {
        loadScope {
            try {
                accountService.signUpWithEmailAndPassword(
                    email = _email.clip(),
                    password = _password.clip()
                )
                accountService.sendEmailVerification()
                onSuccess()
            } catch (e: Exception) {
                Log.e("SignUpWithEmailAndPassword", e.message.toString())
                onFailure()
            }
        }
    }
}