package com.mightysana.onewallet.oneproject.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class Gender {
    MALE, FEMALE, SECRET
}

enum class OneAppState {
    LOADING, OKAY
}

data class DropdownMenuState(
    val expanded: Boolean = false,
    val onItemSelected: (Gender?) -> Unit = {},
    val onExpandedChange: (Boolean) -> Unit = {},
    val onDismissRequest: () -> Unit = {}
)

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}
