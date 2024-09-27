package com.mightysana.onewallet.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {
    var loginState: LoginState = LoginState.Idle
        private set

    fun loginWithGoogle(account: GoogleSignInAccount) {
        loginState = LoginState.Loading
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        viewModelScope.launch {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    loginState = if (task.isSuccessful) {
                        LoginState.Success
                    } else {
                        LoginState.Error(task.exception?.message ?: "Login failed")
                    }
                }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
