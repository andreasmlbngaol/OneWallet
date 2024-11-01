package com.mightysana.onewallet.auth.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mightysana.onewallet.R
import com.mightysana.onewallet.components.OneButton
import com.mightysana.onewallet.components.OneImageButton
import com.mightysana.onewallet.components.OneTextField
import com.mightysana.onewallet.components.OneTextHorizontalDivider

@Composable
fun AuthForm(
    formImage: Painter,
    title: String,
    mainContent: @Composable () -> Unit,
    mainButtonText: String = title,
    onMainButtonClick: () -> Unit = {},
    secondaryContent: @Composable () -> Unit = {},
    navButtonText: String? = null,
    onNavButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
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
            AuthFormImage(
                painter = formImage,
                modifier = Modifier.width(100.dp).height(100.dp)
            )

            AuthFormTitle(
                text = title
            )

            mainContent()

            OneButton(
                onClick = onMainButtonClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = mainButtonText, fontWeight = FontWeight.Bold)
            }

            secondaryContent()

            navButtonText?.let {
                TextButton(
                    onClick = onNavButtonClick,
                ) {
                    Text(text = navButtonText)
                }
            }
        }
    }
}

@Composable
fun AuthOptions(
    horizontalDividerText: String,
    horizontalLineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onSignInWithGoogle: () -> Unit
) {
    OneTextHorizontalDivider(
        text = horizontalDividerText,
        lineColor = horizontalLineColor,
    )

    OneImageButton(
        painter = painterResource(id = R.drawable.google_logo),
        onClick = onSignInWithGoogle,
    )

}

@Composable
fun SignInFormContent(
    email: String,
    password: String,
    visibility: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onVisibilityChange: () -> Unit
) {
    OneTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        labelText = stringResource(R.string.email_label),
        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your email"
    )

    OneTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        labelText = stringResource(R.string.password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your password",
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if (visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onVisibilityChange() },
        autoCorrectEnabled = false
    )
}

@Composable
fun SignUpFormContent(
    email: String,
    password: String,
    confirmPassword: String,
    passwordVisibility: Boolean,
    confirmPasswordVisibility: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit
) {
    OneTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        labelText = stringResource(R.string.email_label),
        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your email"
    )

    OneTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        labelText = stringResource(R.string.password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your password",
        visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onPasswordVisibilityChange() },
        autoCorrectEnabled = false
    )

    OneTextField(
        value = confirmPassword,
        onValueChange = { onConfirmPasswordChange(it) },
        labelText = stringResource(R.string.confirm_password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = "Enter your password",
        visualTransformation = if(confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(confirmPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onConfirmPasswordVisibilityChange() },
        autoCorrectEnabled = false
    )
}


@Composable
fun AuthFormImage(
    painter: Painter,
    modifier: Modifier = Modifier) {
    Image(
        painter = painter,
        contentDescription = "Icon Launcher",
        modifier = modifier
    )
}

@Composable
fun AuthFormTitle(
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
