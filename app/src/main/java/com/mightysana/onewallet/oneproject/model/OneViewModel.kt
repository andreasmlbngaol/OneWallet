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
    val oneRepository = OneRepository()

    fun launchCatching(
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

    protected open val _appState = MutableStateFlow(OneAppState.OKAY)
    open val appState: StateFlow<OneAppState> = _appState

    private fun setAppState(state: OneAppState) {
        _appState.value = state
        Log.d("OneViewModel", _appState.value.toString())
    }

    fun appLoading() {
        setAppState(OneAppState.LOADING)
    }

    fun appOkay() {
        setAppState(OneAppState.OKAY)
    }

    fun loadScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            appLoading()
            block()
            appOkay()
        }
    }

    fun openOtherApp(
        category: String,
        packageName: String,
        context: Context,
        flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
    ) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(category)
            // Intent.CATEGORY_APP_EMAIL
            setPackage(packageName)
            // "com.google.android.gm"
            addFlags(flags)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("openOtherApp", "Error launching other app")
            e.printStackTrace()
        }

    }

}