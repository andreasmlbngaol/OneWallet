package com.mightysana.onewallet.oneproject.auth.presentation.email_verification

import android.content.Context
import android.content.Intent
import android.util.Log
import com.mightysana.onewallet.oneproject.auth.model.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor() : AuthViewModel() {
    private val _emailState = MutableStateFlow(false)

    fun checkEmailVerification(onVerified: suspend () -> Unit) {
        launchCatching {
            while (!_emailState.value) {
                authService.reloadCurrentUser()
                val isEmailVerified = authService.isEmailVerified()
                Log.d("EmailVerificationViewModel", "checkEmailVerification: $isEmailVerified")
                _emailState.value = isEmailVerified
                delay(2000L)
            }
            onVerified()
        }
    }

    val authUserEmail = authService.currentUser!!.email

    fun signOut(
        onSuccess: () -> Unit
    ) {
        loadScope {
            authService.signOut()
            onSuccess()
        }
    }

    fun openEmailApp(context: Context) {
        openOtherApp(
            Intent.CATEGORY_APP_EMAIL,
            "com.google.android.gm",
            context
        )
    }
}

fun String.censoredEmail(): String {
    val index = this.indexOf("@")
    val name = this.substring(0, index)
    val domain = this.substring(index)
    return name.replaceRange(2, name.length - 1, "****") + domain

}