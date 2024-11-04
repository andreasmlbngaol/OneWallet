package com.mightysana.onewallet.oneproject.auth.presentation.register

import android.content.Context
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthService
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import com.mightysana.onewallet.oneproject.auth.presentation.sign_up.SignUpFormValidationResult
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : AuthViewModel() {
    fun onSignOut(onSuccess: () -> Unit) {
        loadScope {
            authService.signOut()
            onSuccess()
        }
    }

    private val _name = MutableStateFlow("")
    val name: MutableStateFlow<String> = _name

    private val _username = MutableStateFlow("")
    val username: MutableStateFlow<String> = _username

    private val _gender = MutableStateFlow("")
    val gender: MutableStateFlow<String> = _gender

    private val _birthDate = MutableStateFlow("")
    val birthDate: MutableStateFlow<String> = _birthDate

    private val _nameError = MutableStateFlow(Pair(false, ""))
    val nameError: MutableStateFlow<Pair<Boolean, String>> = _nameError

    private val _usernameError = MutableStateFlow(Pair(false, ""))
    val usernameError: MutableStateFlow<Pair<Boolean, String>> = _usernameError

    private val _genderError = MutableStateFlow(Pair(false, ""))
    val genderError: MutableStateFlow<Pair<Boolean, String>> = _genderError

    private val _birthDateError = MutableStateFlow(Pair(false, ""))
    val birthDateError: MutableStateFlow<Pair<Boolean, String>> = _birthDateError

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun setGender(newGender: String) {
        _gender.value = newGender
    }

    fun setBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    private fun resetError() {
        _nameError.value = Pair(false, "")
        _usernameError.value = Pair(false, "")
        _genderError.value = Pair(false, "")
        _birthDateError.value = Pair(false, "")

    }

    private fun isNameBlank(): Boolean {
        return _name.value.trim().isBlank()
    }

    private fun isUsernameBlank(): Boolean {
        return _username.value.trim().isBlank()
    }

    private fun isGenderBlank(): Boolean {
        return _gender.value.trim().isBlank()
    }

    private fun isBirthDateBlank(): Boolean {
        return _birthDate.value.trim().isBlank()
    }

    private fun isNameValid(): Boolean {
        return Regex("^[a-zA-Z ]+$").matches(_name.value.trim())
    }

    private fun isUsernameValid(): Boolean {
        return Regex("^[a-zA-Z0-9 ]+$").matches(_username.value.trim())
    }

    private fun nameError(message: String) {
        _nameError.value = Pair(true, message)
    }

    private fun usernameError(message: String) {
        _usernameError.value = Pair(true, message)
    }

    private fun genderError(message: String) {
        _genderError.value = Pair(true, message)
    }

    private fun birthDateError(message: String) {
        _birthDateError.value = Pair(true, message)
    }

    fun validateForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetError()
        val validationState = when {
            isNameBlank() -> RegisterFormValidationResult.NameBlank
            !isNameValid() -> RegisterFormValidationResult.NameNotValid
            isUsernameBlank() -> RegisterFormValidationResult.UsernameBlank
            !isUsernameValid() -> RegisterFormValidationResult.UsernameNotValid
            isGenderBlank() -> RegisterFormValidationResult.GenderBlank
//            !isGenderValid() -> RegisterFormValidationResult.GenderNotValid
            isBirthDateBlank() -> RegisterFormValidationResult.BirthDateBlank
//            !isBirthDateValid() -> RegisterFormValidationResult.BirthDateNotValid
            else -> RegisterFormValidationResult.Valid
        }
        if(validationState is RegisterFormValidationResult.Valid) {
            onSuccess()
        } else {
            when(validationState) {
                is RegisterFormValidationResult.NameBlank -> nameError(context.getString(R.string.email_is_blank))
                is RegisterFormValidationResult.UsernameBlank -> usernameError(context.getString(R.string.email_is_not_valid))
                is RegisterFormValidationResult.GenderBlank -> genderError(context.getString(R.string.password_is_blank))
                is RegisterFormValidationResult.BirthDateBlank -> birthDateError(context.getString(R.string.password_is_not_valid))
                is RegisterFormValidationResult.NameNotValid -> nameError(context.getString(R.string.confirm_password_is_blank))
                is RegisterFormValidationResult.UsernameNotValid -> usernameError(context.getString(R.string.password_and_confirm_password_not_same))
                is RegisterFormValidationResult.GenderNotValid -> genderError(context.getString(R.string.password_and_confirm_password_not_same))
                else -> birthDateError(context.getString(R.string.password_and_confirm_password_not_same))
            }
        }
    }
}

sealed class RegisterFormValidationResult {
    data object Valid : RegisterFormValidationResult()
    data object NameBlank : RegisterFormValidationResult()
    data object UsernameBlank : RegisterFormValidationResult()
    data object GenderBlank : RegisterFormValidationResult()
    data object BirthDateBlank : RegisterFormValidationResult()
    data object NameNotValid : RegisterFormValidationResult()
    data object UsernameNotValid : RegisterFormValidationResult()
    data object GenderNotValid : RegisterFormValidationResult()
    data object BirthDateNotValid : RegisterFormValidationResult()
}