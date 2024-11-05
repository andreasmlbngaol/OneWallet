package com.mightysana.onewallet.oneproject.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.mightysana.onewallet.R
import kotlinx.coroutines.launch

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