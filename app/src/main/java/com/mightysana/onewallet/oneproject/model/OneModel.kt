package com.mightysana.onewallet.oneproject.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.filled.Woman
import androidx.compose.ui.graphics.vector.ImageVector

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

sealed class SignUpFormValidationResult {
    data object Valid : SignUpFormValidationResult()
    data object EmailBlank : SignUpFormValidationResult()
    data object PasswordBlank : SignUpFormValidationResult()
    data object ConfirmPasswordBlank : SignUpFormValidationResult()
    data object PasswordAndConfirmPasswordNotSame : SignUpFormValidationResult()
    data object EmailNotValid : SignUpFormValidationResult()
    data object PasswordNotValid : SignUpFormValidationResult()
}

sealed class RegisterFormValidationResult {
    data object Valid : RegisterFormValidationResult()
    data object NameBlank : RegisterFormValidationResult()
    data object NameNotValid : RegisterFormValidationResult()
    data object GenderBlank : RegisterFormValidationResult()
    data object BirthDateBlank : RegisterFormValidationResult()
}

object OneIcons {
    val Email: ImageVector = Icons.Default.AlternateEmail
    val Password: ImageVector = Icons.Default.Password
    val ConfirmPassword: ImageVector = Icons.Default.Password
    val EmailVerification: ImageVector = Icons.Default.MarkEmailUnread
    val Name: ImageVector = Icons.Default.Person2
    val Gender: ImageVector = Icons.Default.Wc
    val Male: ImageVector = Icons.Default.Man
    val Female: ImageVector = Icons.Default.Woman
    val DropdownExpanded: ImageVector = Icons.Default.ArrowDropUp
    val DropdownCollapsed: ImageVector = Icons.Default.ArrowDropDown
    fun dropdown(expanded: Boolean): ImageVector = if (expanded) DropdownExpanded else DropdownCollapsed
    val DatePicker: ImageVector = Icons.Default.DateRange
    val Secret: ImageVector = Icons.Default.QuestionMark
}

object OneDefault {
    const val CENSORED_CHAR = '•'
}