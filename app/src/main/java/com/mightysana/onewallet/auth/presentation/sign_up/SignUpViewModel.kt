package com.mightysana.onewallet.auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {
    private val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    private val _confirmPasswordVisibility = MutableStateFlow(false)
    val confirmPasswordVisibility: StateFlow<Boolean> = _confirmPasswordVisibility

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
}