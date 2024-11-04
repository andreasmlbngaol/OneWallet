package com.mightysana.onewallet.oneproject.auth.presentation.sign_up

import android.content.Context
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): AuthViewModel() {

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    private val _confirmPasswordVisibility = MutableStateFlow(false)
    val confirmPasswordVisibility: StateFlow<Boolean> = _confirmPasswordVisibility

    private val _confirmPasswordError = MutableStateFlow(Pair(false, ""))
    val confirmPasswordError: StateFlow<Pair<Boolean, String>> = _confirmPasswordError

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

    private fun isConfirmPasswordBlank(): Boolean {
        return _confirmPassword.value.trim().isBlank()
    }

    private fun isPasswordAndConfirmPasswordSame(): Boolean {
        return _password.value.trim() == _confirmPassword.value.trim()
    }

    private fun isPasswordValid(): Boolean {
        return Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$").matches(_password.value.trim())
    }


    private fun confirmPasswordError(message: String) {
        _confirmPasswordError.value = Pair(true, message)
    }

    private fun resetError() {
        _emailError.value = Pair(false, "")
        _passwordError.value = Pair(false, "")
        _confirmPasswordError.value = Pair(false, "")
    }

    fun validateForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetError()
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
                authService.signUpWithEmailAndPassword(
                    _email.value.trim(),
                    _password.value.trim()
                )
                authService.sendEmailVerification()
//                val user = authService.currentUser
//                repository.updateUser(
//                    OneUser(
//                        uid = user!!.uid,
//                        email = user.email!!,
//                        createdAt = user.metadata?.creationTimestamp.toString(),
//                        lastLoginAt = user.metadata?.lastSignInTimestamp.toString()
//                        name = user.displayName,
//                        profilePhotoUrl = user.photoUrl?.toString(),
//                        username = null,
//                        phoneNumber = null,
//                        bio = null,
//                        birthDate = null,
//                        gender = null,
//                        address = null,
//                        verified = false
//                    )
//                )
                onSuccess()
            } catch (e: Exception) {
                onFailure()
            }
        }
    }
}

sealed class SignUpFormValidationResult {
    data object Valid : SignUpFormValidationResult()
    data object EmailBlank : SignUpFormValidationResult()
    data object PasswordBlank : SignUpFormValidationResult()
    data object ConfirmPasswordBlank : SignUpFormValidationResult()
    data object PasswordAndConfirmPasswordNotSame : SignUpFormValidationResult()
    data object EmailNotValid : SignUpFormValidationResult()
    data object PasswordNotValid : SignUpFormValidationResult()
}