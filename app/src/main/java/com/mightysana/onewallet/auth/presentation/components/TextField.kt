package com.mightysana.onewallet.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    labelText: String,
    placeholderText: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    prefixText: String? = null,
    suffixText: String? = null,
    supportingText: String? = null,
    supportingTextColor: Color = MaterialTheme.colorScheme.error,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if(singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onTrailingIconClick: (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = LocalTextStyle.current,
        label = { Text(text = labelText, style = MaterialTheme.typography.labelLarge) },
        placeholder = placeholderText?.let { { Text(text = placeholderText) } },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = if(onTrailingIconClick == null) Modifier else Modifier.clickable { onTrailingIconClick() }
                )
            }
        },
        prefix = prefixText?.let { { Text(text = prefixText) } },
        suffix = suffixText?.let { { Text(text = suffixText) } },
        supportingText = if(isError) supportingText?.let { { Text(text = supportingText, color = supportingTextColor) } } else { null },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions,
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        interactionSource,
        MaterialTheme.shapes.large,
        colors
    )
}