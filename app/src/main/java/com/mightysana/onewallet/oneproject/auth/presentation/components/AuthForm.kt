package com.mightysana.onewallet.oneproject.auth.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import com.mightysana.onewallet.R
import com.mightysana.onewallet.isNotNull
import com.mightysana.onewallet.oneproject.components.GoogleAuthButton
import com.mightysana.onewallet.oneproject.components.OneButton
import com.mightysana.onewallet.oneproject.components.OneTextField
import com.mightysana.onewallet.oneproject.components.OneTextFieldDefault
import com.mightysana.onewallet.oneproject.components.OneTextHorizontalDivider

@Composable
fun AuthForm(
    title: String,
    formImage: Painter? = null,
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            if(formImage.isNotNull()) {
                AuthFormImage(
                    painter = formImage!!,
                    modifier = Modifier.width(100.dp).height(100.dp)
                )
            }
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
    ) { onGetCredentialResponse(it) }

}

@Composable
fun SignInFormContent(
    email: OneTextFieldDefault,
    password: OneTextFieldDefault,
    visibility: Boolean,
    onVisibilityChange: () -> Unit,
) {
    OneTextField(
        value = email.value,
        onValueChange = { email.onValueChange(it) },
        labelText = email.label,
        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = email.errorMessage,
        isError = email.errorMessage.isNotNull()
    )

    OneTextField(
        value = password.value,
        onValueChange = { password.onValueChange(it) },
        labelText = password.label,
        modifier = Modifier.fillMaxWidth(),
        supportingText = password.errorMessage,
        isError = password.errorMessage.isNotNull(),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if (visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onVisibilityChange() },
        autoCorrectEnabled = false
    )
}

@Composable
fun SignUpFormContent(
    email: OneTextFieldDefault,
    password: OneTextFieldDefault,
    confirmPassword: OneTextFieldDefault,
    passwordVisibility: Boolean,
    confirmPasswordVisibility: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit
) {
    OneTextField(
        value = email.value,
        onValueChange = { email.onValueChange(it) },
        labelText = email.label,
        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = email.errorMessage,
        isError = email.errorMessage.isNotNull()
    )

    OneTextField(
        value = password.value,
        onValueChange = { password.onValueChange(it) },
        labelText = password.label,
        modifier = Modifier.fillMaxWidth(),
        supportingText = password.errorMessage,
        isError = password.errorMessage.isNotNull(),
        visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onPasswordVisibilityChange() },
        autoCorrectEnabled = false
    )

    OneTextField(
        value = confirmPassword.value,
        onValueChange = { confirmPassword.onValueChange(it) },
        labelText = stringResource(R.string.confirm_password_label),
        modifier = Modifier.fillMaxWidth(),
        supportingText = confirmPassword.errorMessage,
        isError = confirmPassword.errorMessage.isNotNull(),
        visualTransformation = if(confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(confirmPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = { onConfirmPasswordVisibilityChange() },
        autoCorrectEnabled = false
    )
}


@Composable
fun RegisterFormContent(
    name: OneTextFieldDefault,
    gender: OneTextFieldDefault,
    birthDate: OneTextFieldDefault
) {
    OneTextField(
        value = name.value,
        onValueChange = { name.onValueChange(it) },
        labelText = name.label,
//        leadingIcon = Icons.Default.Info,
        modifier = Modifier.fillMaxWidth(),
        supportingText = name.errorMessage,
        isError = name.errorMessage.isNotNull()
    )

    OneTextField(
        value = gender.value,
        onValueChange = { gender.onValueChange(it) },
        labelText = gender.label,
//        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = gender.errorMessage,
        isError = gender.errorMessage.isNotNull()
    )

    OneTextField(
        value = birthDate.value,
        onValueChange = { birthDate.onValueChange(it) },
        labelText = birthDate.label,
//        leadingIcon = Icons.Default.Person3,
        modifier = Modifier.fillMaxWidth(),
        supportingText = birthDate.errorMessage,
        isError = birthDate.errorMessage.isNotNull()
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
