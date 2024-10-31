package com.mightysana.onewallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OneTextHorizontalDivider(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    thickness: Dp = 1.dp,
    text: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        HorizontalDivider(
            color = lineColor,
            thickness = thickness
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 16.dp)
        )
    }
}