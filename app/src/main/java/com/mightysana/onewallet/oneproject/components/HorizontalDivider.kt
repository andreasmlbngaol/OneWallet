package com.mightysana.onewallet.oneproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OneTextHorizontalDivider(
    modifier: Modifier = Modifier,
    text: String,
    thickness: Dp = 1.dp,
    lineColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        HorizontalDivider(
            color = lineColor,
            thickness = thickness,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            color = lineColor,
            thickness = thickness,
            modifier = Modifier.weight(1f)
        )

    }
}