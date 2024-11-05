package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import android.content.Context
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import com.mightysana.onewallet.oneproject.model.SignInFormValidationResult
import com.mightysana.onewallet.oneproject.model.clip
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : AuthViewModel() {
    fun validateSignInForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetErrors()
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

    fun onSignInWithEmailAndPassword(
        onFailure: (Int) -> Unit,
        onEmailVerified: (Any) -> Unit,
        onEmailNotVerified: () -> Unit
    ) {
        loadScope {
            try {
                authService.signInWithEmailAndPassword(
                    email = _email.clip(),
                    password = _password.clip()
                )
                if (!authService.isEmailVerified())
                    onEmailNotVerified()
                else
                    checkUserRegistrationStatus {
                        onEmailVerified(it)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                val errorCode =
                    if(isEmailBlank() || isPasswordBlank()) 1
                    else 2
                onFailure(errorCode)
            }
        }
    }
}