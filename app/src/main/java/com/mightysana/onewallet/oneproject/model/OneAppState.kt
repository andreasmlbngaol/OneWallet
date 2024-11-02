package com.mightysana.onewallet.oneproject.model

sealed class OneAppState {
    data object Loading : OneAppState()
    data object Okay : OneAppState()
    data class Error(val message: String) : OneAppState()
}