package com.mightysana.onewallet.oneproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.mightysana.onewallet.oneproject.model.OneIcons

@Composable
fun OneDropdownMenuField(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: Map<Any?, String>, // Map<Object?, DisplayText>
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    selectedItem: Any?,
    leadingIcon: @Composable (() -> Unit)? = {
        OneIcon(OneIcons.Gender)
    },
    onItemSelected: (Any?) -> Unit,
    label: @Composable (() -> Unit)?,
    legendText: String = "Select an option",
    supportingText: @Composable (() -> Unit)? = null
) {
    var boxSize by remember { mutableStateOf(IntSize(0, 0)) }
    var textFieldSize by remember { mutableStateOf(IntSize(0, 0)) }
    val focusRequester: FocusRequester = remember { FocusRequester() }
    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                boxSize = coordinates.size
            }
    ) {
        OneTextField(
            value = items[selectedItem] ?: legendText,
            onValueChange = { },
            readOnly = true,
            label = label,
            supportingText = supportingText,
            singleLine = true,
            leadingIcon = leadingIcon,
            trailingIcon = {
                OneIcon(OneIcons.dropdown(expanded))
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .focusRequester(focusRequester)
        )

        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .height(with(LocalDensity.current) { textFieldSize.height.toDp() })
                .clip(MaterialTheme.shapes.large)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onExpandedChange(!expanded)
                            focusRequester.requestFocus()
                        }
                    )
                }
                .background(Color.Transparent)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.width(with(LocalDensity.current) { boxSize.width.toDp() })
        ) {
            DropdownMenuItem(
                text = { Text(legendText) },
                onClick = {
                    onItemSelected(null)
                }
            )

            items.forEach { (obj, displayText) ->
                DropdownMenuItem(
                    text = { Text(displayText) },
                    onClick = {
                        onItemSelected(obj)
                    }
                )
            }
        }
    }
}

@Composable
fun OneDatePickerField(
    value: String,
    label: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    supportingText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTap: () -> Unit
) {
    var boxSize by remember { mutableStateOf(IntSize(0, 0)) }
    var textFieldSize by remember { mutableStateOf(IntSize(0, 0)) }
    val focusRequester: FocusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                boxSize = coordinates.size
            }
    ) {
        OneTextField(
            value = value,
            onValueChange = { },
            readOnly = true,
            label = label,
            supportingText = supportingText,
            singleLine = true,
            trailingIcon = trailingIcon,
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .focusRequester(focusRequester)
        )

        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .height(with(LocalDensity.current) { textFieldSize.height.toDp() })
                .clip(MaterialTheme.shapes.large)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onTap()
                            focusRequester.requestFocus()
                        }
                    )
                }
                .background(Color.Transparent)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneDatePicker(
    confirmButtonText: @Composable () -> Unit,
    onConfirm: () -> Unit,
    dismissButtonText: @Composable () -> Unit,
    onDismiss: () -> Unit,
    datePickerState: DatePickerState,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                confirmButtonText()
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                dismissButtonText()
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}