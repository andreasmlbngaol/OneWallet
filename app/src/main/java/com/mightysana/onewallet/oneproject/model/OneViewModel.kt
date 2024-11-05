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

    private fun setAppState(state: OneAppState) {
        _appState.value = state
    }

    fun appLoading() {
        setAppState(OneAppState.LOADING)
    }

    fun appOkay() {
        setAppState(OneAppState.OKAY)
    }

    protected fun launchCatching(
        exception: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                Log.e("OneViewModel", e.message.toString())
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

    protected fun openOtherApp(
        category: String,
        packageName: String,
        context: Context,
        flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
    ) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(category)
            setPackage(packageName)
            addFlags(flags)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("OneViewModel", e.toString())
            e.printStackTrace()
        }
    }
}