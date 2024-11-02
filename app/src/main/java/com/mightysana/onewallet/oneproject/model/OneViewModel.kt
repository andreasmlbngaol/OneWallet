package com.mightysana.onewallet.oneproject.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class OneViewModel : ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.e("SignInViewModel", throwable.message.orEmpty())
            },
            block = block
        )
    }

    private val _appState: MutableStateFlow<OneAppState> = MutableStateFlow(OneAppState.Okay)
    val appState: StateFlow<OneAppState> = _appState

    private fun setAppState(state: OneAppState) {
        _appState.value = state
        Log.d("OneViewModel", _appState.value.toString())
    }

    fun appLoading() {
        setAppState(OneAppState.Loading)
    }

    fun appOkay() {
        setAppState(OneAppState.Okay)
    }

    fun appError(message: String) {
        setAppState(OneAppState.Error(message))
    }

    fun loadScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            appLoading()
            block()
            appOkay()
        }
    }

}