package com.mightysana.onewallet.oneproject.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import com.mightysana.onewallet.oneproject.components.OneImage
import com.mightysana.onewallet.oneproject.components.OneTextHorizontalDivider
import com.mightysana.onewallet.oneproject.model.isNotNull

@Composable
fun AuthFormCard(
    title: String? = null,
    subtitle: String? = null,
    formImage: Painter? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Form Image
            if(formImage.isNotNull()) {
                OneImage(
                    painter = formImage!!,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
            }

            // Form Title
            if(title.isNotNull()) {
                Text(
                    text = title!!,
                    style = MaterialTheme.typography.headlineLarge,
                    textDecoration = TextDecoration.Underline,
                )
            }

            // Form Subtitle
            if(subtitle.isNotNull()) {
                Text(
                    text = subtitle!!,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // Form Content
            content()
        }
    }
}

@Composable
fun AuthOptions(
    horizontalDividerText: String,
    horizontalLineColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    onLoad: () -> Unit,
    onOkay: () -> Unit,
    onGetCredentialResponse: (Credential) -> Unit
) {
    OneTextHorizontalDivider(
        text = horizontalDividerText,
        lineColor = horizontalLineColor,
    )

    GoogleAuthButton(
        onLoad = onLoad,
        onOkay = onOkay,
    ) {
        onGetCredentialResponse(it)
    }
}