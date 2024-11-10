package com.mightysana.onewallet.main.presentation.main

import com.mightysana.onewallet.main.model.OneWalletViewModel
import com.mightysana.onewallet.oneproject.model.OneProject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): OneWalletViewModel() {
    private val hour = LocalTime.now().hour


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