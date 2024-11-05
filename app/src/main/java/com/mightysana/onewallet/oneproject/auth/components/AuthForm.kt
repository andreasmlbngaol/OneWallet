package com.mightysana.onewallet.oneproject.auth.components

import androidx.compose.foundation.Image
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
import com.mightysana.onewallet.isNotNull
import com.mightysana.onewallet.oneproject.components.OneImage
import com.mightysana.onewallet.oneproject.components.OneTextHorizontalDivider

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
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
fun AuthOtherOptions(
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
    ) {
        onGetCredentialResponse(it)
    }
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


//@Composable
//fun SignInFormContent(
//    email: OneTextFieldDefault,
//    password: OneTextFieldDefault,
//    visibility: Boolean,
//    onVisibilityChange: () -> Unit,
//) {
//    OneTextField(
//        value = email.value,
//        onValueChange = { email.onValueChange(it) },
//        labelText = email.labelText,
//        leadingIcon = Icons.Default.Person3,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = email.errorMessage,
//        isError = email.errorMessage.isNotNull()
//    )
//
//    OneTextField(
//        value = password.value,
//        onValueChange = { password.onValueChange(it) },
//        labelText = password.labelText,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = password.errorMessage,
//        isError = password.errorMessage.isNotNull(),
//        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = if (visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//        leadingIcon = Icons.Default.Key,
//        onTrailingIconClick = { onVisibilityChange() },
//        autoCorrectEnabled = false
//    )
//}

//@Composable
//fun SignUpFormContent(
//    email: OneTextFieldDefault,
//    password: OneTextFieldDefault,
//    confirmPassword: OneTextFieldDefault,
//    passwordVisibility: Boolean,
//    confirmPasswordVisibility: Boolean,
//    onPasswordVisibilityChange: () -> Unit,
//    onConfirmPasswordVisibilityChange: () -> Unit
//) {
//    OneTextField(
//        value = email.value,
//        onValueChange = { email.onValueChange(it) },
//        labelText = email.labelText,
//        leadingIcon = Icons.Default.Person3,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = email.errorMessage,
//        isError = email.errorMessage.isNotNull()
//    )
//
//    OneTextField(
//        value = password.value,
//        onValueChange = { password.onValueChange(it) },
//        labelText = password.labelText,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = password.errorMessage,
//        isError = password.errorMessage.isNotNull(),
//        visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//        leadingIcon = Icons.Default.Key,
//        onTrailingIconClick = { onPasswordVisibilityChange() },
//        autoCorrectEnabled = false
//    )
//
//    OneTextField(
//        value = confirmPassword.value,
//        onValueChange = { confirmPassword.onValueChange(it) },
//        labelText = stringResource(R.string.confirm_password_label),
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = confirmPassword.errorMessage,
//        isError = confirmPassword.errorMessage.isNotNull(),
//        visualTransformation = if(confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = if(confirmPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//        leadingIcon = Icons.Default.Key,
//        onTrailingIconClick = { onConfirmPasswordVisibilityChange() },
//        autoCorrectEnabled = false
//    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RegisterFormContent(
//    name: OneTextFieldDefault,
//    gender: OneDropdownMenuDefault,
//    birthDate: OneTextFieldDefault,
//    onBirthdateTrailingIconClick: () -> Unit,
//    datePickerState: DatePickerState
//) {
//    OneTextField(
//        value = name.value,
//        onValueChange = { name.onValueChange(it) },
//        labelText = name.labelText,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = name.errorMessage,
//        isError = name.errorMessage.isNotNull()
//    )
//
//    OneDropdownFieldMenu(
//        modifier = Modifier.fillMaxWidth(),
//        expanded = gender.expanded,
//        onExpandedChange = { gender.onExpandedChange(it) },
//        selectedItem = gender.selectedItem,
//        labelText = gender.labelText,
//        items = gender.items,
//        onItemSelected = { gender.onItemSelected(it) },
//        onDismissRequest = gender.onDismissRequest,
//    )
//
//    OneTextField(
//        value = birthDate.value,
//        onValueChange = { birthDate.onValueChange(it) },
//        readOnly = true,
//        labelText = birthDate.labelText,
//        modifier = Modifier.fillMaxWidth(),
//        supportingText = birthDate.errorMessage,
//        isError = birthDate.errorMessage.isNotNull(),
//        trailingIcon = Icons.Default.DateRange,
//        onTrailingIconClick = {
//            onBirthdateTrailingIconClick()
//        }
//    )
//
//}
