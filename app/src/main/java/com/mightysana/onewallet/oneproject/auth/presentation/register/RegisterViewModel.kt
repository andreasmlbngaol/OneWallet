package com.mightysana.onewallet.oneproject.auth.presentation.register

import android.content.Context
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val name = _name.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate = _birthDate.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError = _nameError.asStateFlow()

    private val _genderError = MutableStateFlow<String?>(null)
    val genderError = _genderError.asStateFlow()

    private val _birthDateError = MutableStateFlow<String?>(null)
    val birthDateError = _birthDateError.asStateFlow()

    fun setName(newName: String) {
        _name.value = newName
    }


    fun setGender(newGender: String) {
        _gender.value = newGender
    }

    fun setBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    private fun resetError() {
        _nameError.value = null
        _genderError.value = null
        _birthDateError.value = null

    }

    private fun isNameBlank(): Boolean {
        return _name.value.trim().isBlank()
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

    private fun nameError(message: String) {
        _nameError.value = message
    }

    private fun genderError(message: String) {
        _genderError.value = message
    }

    private fun birthDateError(message: String) {
        _birthDateError.value = message
    }

    fun validateForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetError()
        val validationState = when {
            isNameBlank() -> RegisterFormValidationResult.NameBlank
            !isNameValid() -> RegisterFormValidationResult.NameNotValid
            isGenderBlank() -> RegisterFormValidationResult.GenderBlank
            isBirthDateBlank() -> RegisterFormValidationResult.BirthDateBlank
            else -> RegisterFormValidationResult.Valid
        }
        if(validationState is RegisterFormValidationResult.Valid) {
            onSuccess()
        } else {
            when(validationState) {
                is RegisterFormValidationResult.NameBlank -> nameError(context.getString(R.string.name_is_blank))
                is RegisterFormValidationResult.NameNotValid -> nameError(context.getString(R.string.name_is_not_valid))
                is RegisterFormValidationResult.GenderBlank -> genderError(context.getString(R.string.gender_is_blank))
                else -> birthDateError(context.getString(R.string.birth_date_is_blank))
            }
        }
    }

    fun register(onSuccess: () -> Unit) {

    }
}

sealed class RegisterFormValidationResult {
    data object Valid : RegisterFormValidationResult()
    data object NameBlank : RegisterFormValidationResult()
    data object NameNotValid : RegisterFormValidationResult()
    data object GenderBlank : RegisterFormValidationResult()
    data object BirthDateBlank : RegisterFormValidationResult()
}