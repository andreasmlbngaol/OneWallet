package com.mightysana.onewallet.oneproject.auth.functions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

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