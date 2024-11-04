package com.mightysana.onewallet.oneproject.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow

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
    autoCorrectEnabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = LocalTextStyle.current,
        label = { Text(text = labelText, style = MaterialTheme.typography.labelLarge, overflow = TextOverflow.Ellipsis) },
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
                val icon = Icon(
                    imageVector = it,
                    contentDescription = null,
                )
                if(onTrailingIconClick == null) icon else IconButton(onClick = onTrailingIconClick, content = { icon })
            }
        },
        prefix = prefixText?.let { { Text(text = prefixText) } },
        suffix = suffixText?.let { { Text(text = suffixText) } },
        supportingText = {
            AnimatedVisibility(isError) {
                Text(text = supportingText!!, color = supportingTextColor)
            }
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = if(autoCorrectEnabled) keyboardOptions else KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        interactionSource,
        MaterialTheme.shapes.large,
        colors
    )
}