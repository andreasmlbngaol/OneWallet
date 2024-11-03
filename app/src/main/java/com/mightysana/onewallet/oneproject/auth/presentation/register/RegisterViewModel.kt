package com.mightysana.onewallet.oneproject.auth.presentation.register

import com.mightysana.onewallet.oneproject.auth.model.AuthService
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService
) : OneViewModel() {
    fun onSignOut(onSuccess: () -> Unit) {
        loadScope {
            authService.signOut()
            onSuccess()
        }
    }

}