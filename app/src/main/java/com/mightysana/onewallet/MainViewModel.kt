package com.mightysana.onewallet

import androidx.lifecycle.viewModelScope
import com.mightysana.onewallet.oneproject.auth.EmailVerification
import com.mightysana.onewallet.oneproject.auth.Register
import com.mightysana.onewallet.oneproject.auth.SignIn
import com.mightysana.onewallet.oneproject.auth.model.AuthService
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authServices: AuthService
): OneViewModel() {

    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            authServices.reloadUser()

            if(!isUserLoggedIn()) {
                _startDestination.value = SignIn
            } else if(!isUserVerified()) {
                _startDestination.value = EmailVerification
            } else if(!isUserRegistered()) {
                _startDestination.value = Register
            } else {
                _startDestination.value = Home
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return authServices.currentUser.isNotNull()
    }

    fun isUserVerified(): Boolean{
        return authServices.currentUser!!.isEmailVerified
    }

    suspend fun isUserRegistered(): Boolean {
        return oneRepository.usersRef
            .child(currentUser!!.uid)
            .get()
            .await()
            .exists()
    }

}