package com.mightysana.onewallet.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mightysana.onewallet.R
import com.mightysana.onewallet.auth.presentation.components.AuthFormImage
import com.mightysana.onewallet.auth.presentation.components.AuthFormTitle
import com.mightysana.onewallet.components.OneTextField
import com.mightysana.onewallet.components.OneButton
import com.mightysana.onewallet.components.OneImageButton
import com.mightysana.onewallet.components.OneTextHorizontalDivider
import com.mightysana.onewallet.components.Preview

@Composable
fun RegisterForm(
    painter: Painter,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onSignInWithGoogle: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            AuthFormImage(painter, modifier = Modifier.width(100.dp).height(100.dp))
            AuthFormTitle(stringResource(R.string.register_title))
            RegisterFormInput(
                onNavigateToLogin = onNavigateToLogin,
                onRegister = onRegister,
                onSignInWithGoogle = onSignInWithGoogle
            )
        }
    }
}

@Composable
fun RegisterFormInput(
    onNavigateToLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onSignInWithGoogle: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    OneTextField(
        value = email,
        onValueChange = { email = it },
        labelText = stringResource(R.string.email_label),
        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your email"
    )

    OneTextField(
        value = password,
        onValueChange = { password = it },
        labelText = stringResource(R.string.password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your password",
        visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = {
            passwordVisibility = !passwordVisibility
        },
        autoCorrectEnabled = false
    )

    OneTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        labelText = stringResource(R.string.confirm_password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your password",
        visualTransformation = if(confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(confirmPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = {
            confirmPasswordVisibility = !confirmPasswordVisibility
        },
        autoCorrectEnabled = false
    )


    OneButton(
        onClick = onRegister,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.register_title), fontWeight = FontWeight.Bold)
    }

    OneTextHorizontalDivider(
        text = stringResource(R.string.or_continue_with),
        lineColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    OneImageButton(
        painter = painterResource(id = R.drawable.google_logo),
        onClick = onSignInWithGoogle
    )

    TextButton(
        onClick = onNavigateToLogin,
    ) {
        Text(text = stringResource(R.string.already_have_account))
    }
}

@PreviewLightDark
@Composable
fun RegisterFormPreview() {
    Preview {
        RegisterForm(
            painter = painterResource(id = R.drawable.one_wallet_logo_round)
        )
    }
}