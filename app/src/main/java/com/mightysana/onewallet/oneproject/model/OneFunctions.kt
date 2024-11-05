package com.mightysana.onewallet.oneproject.model

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Context.toast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(
    @StringRes messageResource: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, this.getString(messageResource), duration).show()
}

fun MutableStateFlow<String>.clip(): String {
    return this.value.trim()
}