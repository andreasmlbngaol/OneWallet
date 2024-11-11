package com.mightysana.onewallet.main.presentation.main

import android.content.Context
import com.mightysana.onewallet.main.model.OneWalletViewModel
import com.mightysana.onewallet.oneproject.model.OneProject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
): OneWalletViewModel(context) {
    private val hour = LocalTime.now().hour

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()

    private fun setExpanded(expanded: Boolean) {
        _expanded.value = expanded
    }

    fun toggleExpanded() {
        setExpanded(!expanded.value)
    }

    fun greetings(
        morning: String,
        day: String,
        afternoon: String,
        evening: String,
    ): String {
        return when(hour) {
            in OneProject.Morning -> morning
            in OneProject.Day -> day
            in OneProject.Afternoon -> afternoon
            else -> evening
        }
    }

}