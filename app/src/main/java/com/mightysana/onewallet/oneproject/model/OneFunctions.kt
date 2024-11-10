package com.mightysana.onewallet.oneproject.model

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.convertMillisToDateTime(): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(Date(this))
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

fun String.censoredEmail(): String {
    val index = this.indexOf("@")
    val name = this.substring(0, index)
    val domain = this.substring(index)
    return name.replaceRange(2, name.length - 1, "****") + domain
}

fun NavHostController.navigateAndPopUp(route: Any, popUp: Any) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}

fun Any?.isNotNull(): Boolean = this != null

//fun Any.qualifiedName(): String? = this::class.qualifiedName

fun String.getObject(): Any? {
    return Class.forName(this).kotlin.objectInstance
}