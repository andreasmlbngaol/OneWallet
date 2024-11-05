package com.mightysana.onewallet.oneproject.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mightysana.onewallet.isNotNull

@Composable
fun ErrorSupportingText(
    message: String?
) {
    AnimatedVisibility(message.isNotNull()) {
        Text(
            text = message!!,
            color = MaterialTheme.colorScheme.error
        )
    }
}