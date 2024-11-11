package com.mightysana.onewallet.oneproject.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mightysana.onewallet.oneproject.auth.model.service.AccountService
import com.mightysana.onewallet.oneproject.auth.model.service.AccountServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class OneViewModel(
    context: Context
) : ViewModel() {
    protected val oneRepository: OneRepository = OneRepository()
    protected val accountService: AccountService = AccountServiceImpl()

    private val networkStatusTracker: NetworkStatusTracker = NetworkStatusTracker(context)
    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    private val _appState = MutableStateFlow(OneAppState.OKAY)
    val appState: StateFlow<OneAppState> = _appState

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkStatusTracker.isNetworkAvailable.collectLatest { isConnected ->
                _isNetworkAvailable.value = isConnected
                Log.d("OneViewModel", "Network Available: ${_isNetworkAvailable.value}")
            }
        }
    }

    init {
        observeNetworkStatus()
    }

    private fun setAppState(state: OneAppState) {
        _appState.value = state
        Log.d("OneViewModel", "${_appState.value}")
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

    fun onSignOut(onSuccess: () -> Unit) {
        loadScope {
            accountService.signOut()
            onSuccess()
        }
    }
}