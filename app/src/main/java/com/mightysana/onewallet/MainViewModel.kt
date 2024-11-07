package com.mightysana.onewallet

import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.oneproject.auth.model.EmailVerification
import com.mightysana.onewallet.oneproject.auth.model.Register
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.auth.model.service.AccountService
import com.mightysana.onewallet.oneproject.model.OneViewModel
import com.mightysana.onewallet.oneproject.model.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountServices: AccountService
): OneViewModel() {
    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    private fun isUserLoggedIn(): Boolean {
        return accountServices.currentUser.isNotNull()
    }

    private fun isUserVerified(): Boolean{
        return accountServices.currentUser!!.isEmailVerified
    }

    private suspend fun isUserRegistered(): Boolean {
        return oneRepository.usersRef
            .child(Firebase.auth.currentUser!!.uid)
            .get()
            .await()
            .exists()
    }

    init {
        appLoading()
        viewModelScope.launch {
            accountServices.reloadUser()

            if(!isUserLoggedIn()) {
                _startDestination.value = SignIn
            } else if(!isUserVerified()) {
                _startDestination.value = EmailVerification
            } else if(!isUserRegistered()) {
                _startDestination.value = Register
            } else {
                _startDestination.value = Home
            }
            appOkay()
        }
    }
}