package com.mightysana.onewallet.oneproject.auth.presentation.email_verification

import com.mightysana.onewallet.oneproject.auth.model.AuthService
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authService: AuthService
) : OneViewModel() {
    private val _emailState = MutableStateFlow(false)
    val emailState = _emailState.asStateFlow()

    fun checkEmail() {
        launchCatching {
            _emailState.value = authService.isEmailVerified()
        }
    }
}