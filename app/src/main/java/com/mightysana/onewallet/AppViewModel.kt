package com.mightysana.onewallet

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.oneproject.auth.model.AuthGraph
import com.mightysana.onewallet.oneproject.auth.model.EmailVerification
import com.mightysana.onewallet.oneproject.auth.model.Register
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.model.OneViewModel
import com.mightysana.onewallet.oneproject.model.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    @ApplicationContext context: Context
): OneViewModel(context) {
    private val _startDestination = MutableStateFlow<Any?>(AuthGraph)
    val startDestination = _startDestination.asStateFlow()

    private val _authStartDestination = MutableStateFlow<Any?>(SignIn)
    val authStartDestination = _authStartDestination.asStateFlow()

    private fun isUserLoggedIn(): Boolean {
        return accountService.currentUser.isNotNull()
    }

    private fun isUserVerified(): Boolean{
        return accountService.currentUser!!.isEmailVerified
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
            accountService.reloadUser()

            if(!isUserLoggedIn()) {
                _authStartDestination.value = SignIn
            } else if(!isUserVerified()) {
                _authStartDestination.value = EmailVerification
            } else if(!isUserRegistered()) {
                _authStartDestination.value = Register
            } else {
                _startDestination.value = Main
            }
            Log.d("MainViewModel", _startDestination.value.toString())
            appOkay()
        }
    }
}