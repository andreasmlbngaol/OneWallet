package com.mightysana.onewallet.oneproject.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}