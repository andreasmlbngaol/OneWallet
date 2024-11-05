package com.mightysana.onewallet.oneproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun OneDropdownFieldMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: Map<Any?, String>, // Map<Object?, DisplayText>
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    selectedItem: Any?,
    onItemSelected: (Any?) -> Unit,
    label: @Composable () -> Unit,
    legendText: String = "Select an option"
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
        OutlinedTextField(
            value = items[selectedItem]!!,
            onValueChange = { },
            readOnly = true,
            label = label,
            trailingIcon = {
                Icon (
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
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
                }.background(Color.Transparent)
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