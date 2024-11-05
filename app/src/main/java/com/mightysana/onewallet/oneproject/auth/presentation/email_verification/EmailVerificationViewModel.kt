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
    private val _isEmailVerified = MutableStateFlow(false)

    val authUserEmail = authService.currentUser!!.email

    fun checkEmailVerification(onVerified: suspend () -> Unit) {
        launchCatching {
            while (!_isEmailVerified.value) {
                authService.reloadCurrentUser()
                val isEmailVerified = authService.isEmailVerified()
                Log.d("EmailVerificationViewModel", "checkEmailVerification: $isEmailVerified")
                _isEmailVerified.value = isEmailVerified
                delay(1500L)
            }
            onVerified()
        }
    }

    fun openEmailApp(context: Context) {
        openOtherApp(
            category = Intent.CATEGORY_APP_EMAIL,
            packageName = "com.google.android.gm",
            context = context
        )
    }
}