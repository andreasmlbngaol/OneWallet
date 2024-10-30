package com.superbgoal.onewallet.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbgoal.onewallet.R
import com.superbgoal.onewallet.auth.presentation.components.OneTextField
import com.superbgoal.onewallet.components.OneButton
import com.superbgoal.onewallet.components.OneImageButton
import com.superbgoal.onewallet.components.OneTextHorizontalDivider

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LoginForm(Modifier.fillMaxWidth(0.85f))
        }
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }

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
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Icon Launcher",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                textDecoration = TextDecoration.Underline
            )
            OneTextField(
                value = email,
                onValueChange = { email = it },
                labelText = "Email",
                leadingIcon = Icons.Default.Person3,
                modifier = Modifier.fillMaxWidth(),
                supportingText = "Enter your email"
            )

            OneTextField(
                value = password,
                onValueChange = { password = it },
                labelText = "Password",
                modifier = Modifier.fillMaxWidth(),
                supportingText = "Enter your password",
                visualTransformation = if(visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = if(visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                leadingIcon = Icons.Default.Key,
                onTrailingIconClick = {
                    visibility = !visibility
                }
            )

            OneButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login", fontWeight = FontWeight.Bold)
            }
            OneTextHorizontalDivider(
                text = "or login with",
                lineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            OneImageButton(
                painter = painterResource(id = R.drawable.google_logo),
                onClick = {}
            )
        }
    }
}