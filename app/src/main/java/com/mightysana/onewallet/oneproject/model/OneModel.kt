package com.mightysana.onewallet.oneproject.model

enum class Gender {
    MALE, FEMALE, SECRET
}

enum class OneAppState {
    LOADING, OKAY
}

sealed class SignInFormValidationResult {
    data object Valid : SignInFormValidationResult()
    data object EmailBlank : SignInFormValidationResult()
    data object EmailInvalid : SignInFormValidationResult()
    data object PasswordBlank : SignInFormValidationResult()
}