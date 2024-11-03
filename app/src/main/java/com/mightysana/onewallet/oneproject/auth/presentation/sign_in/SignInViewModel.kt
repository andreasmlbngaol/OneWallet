package com.mightysana.onewallet.oneproject.auth.presentation.sign_in

import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : AuthViewModel() {
    private val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun onSignInWithEmailAndPassword(
        onFailure: (Int) -> Unit,
        onEmailVerified: (Any) -> Unit,
        onEmailNotVerified: () -> Unit
    ) {
        loadScope {
            try {
                authService.signInWithEmailAndPassword(_email.value.trim(), _password.value.trim())
                if (!authService.isEmailVerified()) onEmailNotVerified() else checkUserRegistrationStatus { onEmailVerified(it) }
            } catch (e: Exception) {
                val errorCode = if(_email.value.trim().isEmpty() || _password.value.trim().isEmpty()) 1 else 2
                onFailure(errorCode)
            }
        }
    }
}