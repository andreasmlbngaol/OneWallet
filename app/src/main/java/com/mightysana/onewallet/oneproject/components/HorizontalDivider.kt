package com.mightysana.onewallet.oneproject.components

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OneTextHorizontalDivider(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    thickness: Dp = 1.dp,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium
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
            style = style,
            modifier = Modifier
                .background(backgroundColor)
                .padding(horizontal = 16.dp)
        )
    }
}