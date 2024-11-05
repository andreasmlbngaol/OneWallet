package com.mightysana.onewallet.oneproject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 50.dp,
    height: Dp = 50.dp
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painter,
            contentDescription = painter.toString(),
            modifier = Modifier
                .width(width)
                .height(height)
        )
    }
}