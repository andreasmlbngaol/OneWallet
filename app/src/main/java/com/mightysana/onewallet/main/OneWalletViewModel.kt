package com.mightysana.onewallet.main

import com.mightysana.onewallet.oneproject.model.OneUser
import com.mightysana.onewallet.oneproject.model.OneViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class OneWalletViewModel @Inject constructor(): OneViewModel() {
    private val _userProfile = MutableStateFlow(OneUser())
    val userProfile = _userProfile.asStateFlow()

    init {
        observeUserProfile()
    }

    private fun observeUserProfile() {
        oneRepository.observeUserProfile(
            userId = accountService.userId!!,
        ) {
            _userProfile.value = it
        }
    }
}