package com.mightysana.onewallet.main.presentation.dashboard

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.main.model.OneWalletViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext context: Context
) : OneWalletViewModel(context) {
    private val _isWalletUser = MutableStateFlow(false)
    val isWalletUser = _isWalletUser.asStateFlow()

    private suspend fun isWalletUser(): Boolean {
        return oneRepository.oneWalletRef
            .child(Firebase.auth.currentUser!!.uid)
            .get()
            .await()
            .exists()
    }
}