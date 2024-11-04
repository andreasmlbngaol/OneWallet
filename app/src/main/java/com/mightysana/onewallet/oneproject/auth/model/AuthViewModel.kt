package com.mightysana.onewallet.oneproject.auth.model

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.model.impl.AuthServiceImpl
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor() : OneViewModel() {
    protected val authService: AuthService = AuthServiceImpl()

    protected val _email =  MutableStateFlow("")
    val email: StateFlow<String> = _email

    protected val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    protected val _emailError = MutableStateFlow(Pair(false, ""))
    val emailError: StateFlow<Pair<Boolean, String>> = _emailError

    protected val _passwordError = MutableStateFlow(Pair(false, ""))
    val passwordError: StateFlow<Pair<Boolean, String>> = _passwordError

    protected fun emailError(message: String) {
        _emailError.value = Pair(true, message)
    }

    protected fun passwordError(message: String) {
        _passwordError.value = Pair(true, message)
    }

    fun onSignInWithGoogle(
        credential: Credential,
        onFailure: () -> Unit,
        onSuccess: (Any) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    authService.signInWithGoogle(googleIdTokenCredential.idToken)
                }
                while (authService.currentUser == null) {
                    delay(100L)  // Cek setiap 100ms sampai currentUser tidak null
                }

                checkUserRegistrationStatus { destination ->
                    onSuccess(destination)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "onSignInWithGoogle: $e")
                onFailure()
            }
        }
    }

    fun checkUserRegistrationStatus(destinationRoute: (Any) -> Unit) {
        viewModelScope.launch {
            destinationRoute(if (oneRepository.isUserRegistered(authService.currentUser!!.uid)) Home else Register)
        }
    }

    protected fun isEmailBlank(): Boolean {
        return _email.value.trim().isBlank()
    }

    protected fun isPasswordBlank(): Boolean {
        return _password.value.trim().isBlank()
    }

    protected fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value.trim()).matches()
    }


}