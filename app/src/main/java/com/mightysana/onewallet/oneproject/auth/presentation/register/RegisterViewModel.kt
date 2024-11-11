package com.mightysana.onewallet.oneproject.auth.presentation.register

import android.content.Context
import android.util.Log
import com.mightysana.onewallet.R
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import com.mightysana.onewallet.oneproject.model.Gender
import com.mightysana.onewallet.oneproject.model.OneUser
import com.mightysana.onewallet.oneproject.model.RegisterFormValidationResult
import com.mightysana.onewallet.oneproject.model.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext context: Context
) : AuthViewModel(context) {
    // Declaration
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _gender: MutableStateFlow<Gender?> = MutableStateFlow(null)
    val gender = _gender.asStateFlow()

    private val _birthDate = MutableStateFlow(0L)
    val birthDate = _birthDate.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError = _nameError.asStateFlow()

    private val _genderError = MutableStateFlow<String?>(null)
    val genderError = _genderError.asStateFlow()

    private val _birthDateError = MutableStateFlow<String?>(null)
    val birthDateError = _birthDateError.asStateFlow()

    private val _genderExpanded = MutableStateFlow(false)
    val genderExpanded = _genderExpanded.asStateFlow()

    private val _datePickerVisible = MutableStateFlow(false)
    val datePickerVisible = _datePickerVisible.asStateFlow()

    // Assignment
    fun setName(newName: String) {
        _name.value = newName
    }

    fun setGender(newGender: Gender?) {
        _gender.value = newGender
        collapseGender()
    }

    fun setBirthDate(date: Long) {
        _birthDate.value = date
        dismissDatePicker()
    }

    private fun setNameError(message: String) {
        _nameError.value = message
    }

    private fun setGenderError(message: String) {
        _genderError.value = message
    }

    private fun setBirthdateError(message: String) {
        _birthDateError.value = message
    }

    fun toggleGenderExpanded() {
        _genderExpanded.value = !_genderExpanded.value
    }

    fun collapseGender() {
        _genderExpanded.value = false
    }

    private fun setShowDatePicker(show: Boolean) {
        _datePickerVisible.value = show
    }

    fun showDatePicker() {
        setShowDatePicker(true)
    }

    fun dismissDatePicker() {
        setShowDatePicker(false)
    }

    // Reset Values
    fun resetNameError() {
        _nameError.value = null
    }

    fun resetGenderError() {
        _genderError.value = null
    }

    fun resetBirthdateError() {
        _birthDateError.value = null
    }

    override fun resetErrors() {
        resetNameError()
        resetGenderError()
        resetBirthdateError()
    }

    // Form Checking
    private fun isNameBlank(): Boolean {
        return _name.value.trim().isBlank()
    }

    private fun isGenderBlank(): Boolean {
        return !_gender.value.isNotNull()
    }

    private fun isBirthDateBlank(): Boolean {
        return _birthDate.value == 0L
    }

    private fun isNameValid(): Boolean {
        return Regex("^[a-zA-Z ]+$").matches(_name.value.trim())
    }

    fun validateRegisterForm(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        resetErrors()
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
                is RegisterFormValidationResult.NameBlank -> setNameError(context.getString(R.string.name_is_blank))
                is RegisterFormValidationResult.NameNotValid -> setNameError(context.getString(R.string.name_is_not_valid))
                is RegisterFormValidationResult.GenderBlank -> setGenderError(context.getString(R.string.gender_is_blank))
                else -> setBirthdateError(context.getString(R.string.birth_date_is_blank))
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        loadScope {
            try {
                val user = accountService.currentUser
                oneRepository.updateUser(
                    OneUser(
                        uid = user!!.uid,
                        name = _name.value,
                        email = user.email!!,
                        profilePhotoUrl = user.photoUrl?.toString(),
                        birthDate = _birthDate.value,
                        gender = _gender.value?.name,
                        createdAt = user.metadata!!.creationTimestamp,
                        lastLoginAt = user.metadata!!.lastSignInTimestamp,
                        verified = true
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "register: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}