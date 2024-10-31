package com.mightysana.onewallet.auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import com.mightysana.onewallet.R

@Composable
fun FormImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.bayern_logo_foreground),
        contentDescription = "Icon Launcher",
        modifier = modifier
    )
}

@Composable
fun FormTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        textDecoration = TextDecoration.Underline,
        modifier = modifier
    )
}
