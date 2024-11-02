package com.mightysana.onewallet.oneproject.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class OneViewModel : ViewModel() {
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

//    fun appError(message: String) {
//        setAppState(OneAppState.Error(message))
//    }

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