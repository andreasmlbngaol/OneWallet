package com.mightysana.onewallet.auth.presentation.login

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mightysana.onewallet.R
import com.mightysana.onewallet.auth.presentation.components.FormImage
import com.mightysana.onewallet.auth.presentation.components.FormTitle
import com.mightysana.onewallet.auth.presentation.components.OneTextField
import com.mightysana.onewallet.components.OneButton
import com.mightysana.onewallet.components.OneImageButton
import com.mightysana.onewallet.components.OneTextHorizontalDivider
import com.mightysana.onewallet.components.Preview

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit = {},
    onLogin: () -> Unit = {},
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
            FormImage(modifier = Modifier.width(100.dp).height(100.dp))
            FormTitle(stringResource(R.string.login_title))
            LoginFormInput(
                onNavigateToRegister = onNavigateToRegister,
                onLogin = onLogin,
                onSignInWithGoogle = onSignInWithGoogle
            )
        }
    }
}

@Composable
fun LoginFormInput(
    onNavigateToRegister: () -> Unit = {},
    onLogin: () -> Unit = {},
    onSignInWithGoogle: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
//    var emailIsError by remember { mutableStateOf(false) }
//    var passwordIsError by remember { mutableStateOf(false) }

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
        visualTransformation = if(visibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        leadingIcon = Icons.Default.Key,
        onTrailingIconClick = {
            visibility = !visibility
        },
        autoCorrectEnabled = false
    )

    OneButton(
        onClick = onLogin,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.login_title), fontWeight = FontWeight.Bold)
    }

    OneTextHorizontalDivider(
        text = stringResource(R.string.or_continue_with),
        lineColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    OneImageButton(
        painter = painterResource(id = R.drawable.google_logo),
        onClick = onSignInWithGoogle,
    )

    TextButton(
        onClick = onNavigateToRegister,
    ) {
        Text(text = stringResource(R.string.dont_have_an_account))
    }
}

@PreviewLightDark
@Composable
fun LoginFormPreview() {
    Preview {
        LoginForm()
    }
}