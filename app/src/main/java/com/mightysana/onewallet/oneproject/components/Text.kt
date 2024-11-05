package com.mightysana.onewallet.oneproject.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorSupportingText(
    message: String?
) {
    AnimatedVisibility(message != null) {
        Text(
            text = message?.toString() ?: "",
            color = MaterialTheme.colorScheme.error
        )
    }
}