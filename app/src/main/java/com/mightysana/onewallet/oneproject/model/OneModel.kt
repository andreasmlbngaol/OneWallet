package com.mightysana.onewallet.oneproject.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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

data class BottomNavBarItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val labelResId:  Int,
    val route: Any
)


object OneIcons {
    val Plus: ImageVector = Icons.Default.Add
    val Minus: ImageVector = Icons.Default.Remove
    val DashboardSelected: ImageVector = Icons.Filled.Home
    val DashboardUnselected: ImageVector = Icons.Outlined.Home
    val TransactionsSelected: ImageVector = Icons.Filled.Analytics
    val TransactionsUnselected: ImageVector = Icons.Outlined.Analytics
    val WalletsSelected: ImageVector = Icons.Filled.Savings
    val WalletsUnselected: ImageVector = Icons.Outlined.Savings
    val DebtsSelected: ImageVector = Icons.Filled.MonetizationOn
    val DebtsUnselected: ImageVector = Icons.Outlined.MonetizationOn
    val Profile: ImageVector = Icons.Filled.Person
    val Email: ImageVector = Icons.Default.AlternateEmail
//    val Logout: ImageVector = Icons.AutoMirrored.Filled.Logout
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

object OneProject {
    const val CENSORED_CHARACTER = '•'
    val MaxFormWidth = 500.dp
    val HorizontalPadding = 16.dp
    val TopPadding = 8.dp
    const val USERS_REF = "users"
    const val ONE_WALLET_REF = "one_wallet"
    val Morning = 4..10
    val Day = 11..14
    val Afternoon = 15..18
}