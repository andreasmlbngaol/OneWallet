package com.mightysana.onewallet.oneproject.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OneViewModel @Inject constructor() : ViewModel() {
    protected val oneRepository = OneRepository()

    private val _appState = MutableStateFlow(OneAppState.OKAY)
    val appState: StateFlow<OneAppState> = _appState

    protected fun launchCatching(
        exception: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                exception(e)
            }
        }
    }

    protected fun loadScope(block: suspend CoroutineScope.() -> Unit) {
        launchCatching {
            appLoading()
            block()
            appOkay()
        }
    }

    private fun setAppState(state: OneAppState) {
        _appState.value = state
    }

    protected fun appLoading() {
        setAppState(OneAppState.LOADING)
    }

    protected fun appOkay() {
        setAppState(OneAppState.OKAY)
    }


    protected fun openOtherApp(
        category: String,
        packageName: String,
        context: Context,
        flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
    ) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(category) // Intent.CATEGORY_APP_EMAIL
            setPackage(packageName) // "com.google.android.gm"
            addFlags(flags)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("OneViewModel", e.toString())
        }
    }
}