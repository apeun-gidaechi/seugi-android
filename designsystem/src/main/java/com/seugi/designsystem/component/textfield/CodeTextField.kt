package com.seugi.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.theme.SeugiTheme

/**
 * Seugi TextField
 *
 * @param modifier the Modifier to be applied to this text field
 * @param value the means the value to be seen.
 * @param limit the indicates the maximum number to be displayed.
 * @param onValueChange the event is forwarded when the value changes.
 * @param isError the if true, change the color of the border.
 * @param enabled the whether TextField can be entered.
 * @param textStyle the setting for when the value is displayed.
 * @param shape the setting for how round the textField is.
 * @param colors specifies the color of the text field.
 */
@Composable
fun SeugiCodeTextField(
    modifier: Modifier = Modifier,
    value: String,
    limit: Int,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = SeugiTheme.typography.subtitle2,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = SeugiTheme.colors.black,
        focusedContainerColor = SeugiTheme.colors.white,
        focusedIndicatorColor = SeugiTheme.colors.transparent,
        unfocusedContainerColor = SeugiTheme.colors.white,
        unfocusedTextColor = SeugiTheme.colors.black,
        unfocusedIndicatorColor = SeugiTheme.colors.transparent,
        disabledIndicatorColor = SeugiTheme.colors.transparent,
        disabledTextColor = SeugiTheme.colors.gray400,
        disabledContainerColor = SeugiTheme.colors.white,
    ),
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = LocalFocusManager.current

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            value = value,
            modifier = modifier
                .height(52.dp)
                .background(
                    color = if (enabled) colors.focusedContainerColor else colors.disabledContainerColor,
                    shape = shape,
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            onValueChange = { newValue ->
                if (newValue.length > limit) {
                    return@BasicTextField
                }
                onValueChange(newValue)
            },
            enabled = enabled,
            textStyle = textStyle,
            singleLine = true,
            cursorBrush = SolidColor(SeugiTheme.colors.primary500),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.clearFocus(true)
                },
            ),
            decorationBox = @Composable {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(limit) { index ->
                        Box(
                            modifier = Modifier
                                .height(52.dp)
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        isError -> SeugiTheme.colors.red500
                                        isFocused && value.length == index || isFocused && limit - index == 1 &&
                                            value.length == limit -> SeugiTheme.colors.primary500
                                        enabled -> SeugiTheme.colors.gray300
                                        else -> SeugiTheme.colors.gray200
                                    },
                                    shape = shape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentHeight(),
                                text = value.getOrNull(index)?.toString() ?: "",
                                style = textStyle,
                                color = if (enabled) colors.focusedTextColor else colors.disabledTextColor,
                                textAlign = TextAlign.Center,
                            )
                        }
                        if (index != limit - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeugiCodeTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    limit: Int,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = SeugiTheme.typography.subtitle2,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = SeugiTheme.colors.black,
        focusedContainerColor = SeugiTheme.colors.white,
        focusedIndicatorColor = SeugiTheme.colors.transparent,
        unfocusedContainerColor = SeugiTheme.colors.white,
        unfocusedTextColor = SeugiTheme.colors.black,
        unfocusedIndicatorColor = SeugiTheme.colors.transparent,
        disabledIndicatorColor = SeugiTheme.colors.transparent,
        disabledTextColor = SeugiTheme.colors.gray400,
        disabledContainerColor = SeugiTheme.colors.white,
    ),
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = LocalFocusManager.current

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            value = value,
            modifier = modifier
                .height(52.dp)
                .background(
                    color = if (enabled) colors.focusedContainerColor else colors.disabledContainerColor,
                    shape = shape,
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            onValueChange = { newValue ->
                if (newValue.text.length > limit) {
                    return@BasicTextField
                }
                onValueChange(newValue)
            },
            enabled = enabled,
            textStyle = textStyle,
            singleLine = true,
            cursorBrush = SolidColor(SeugiTheme.colors.primary500),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.clearFocus(true)
                },
            ),
            decorationBox = @Composable {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(limit) { index ->

                        Box(
                            modifier = Modifier
                                .height(52.dp)
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        isError -> SeugiTheme.colors.red500
                                        isFocused && value.text.length == index || isFocused && limit - index == 1 && value.text.length == limit -> SeugiTheme.colors.primary500
                                        enabled -> SeugiTheme.colors.gray300
                                        else -> SeugiTheme.colors.gray200
                                    },
                                    shape = shape,
                                )
                                .padding(horizontal = 18.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentHeight(),
                                text = value.text.getOrNull(index)?.toString() ?: "",
                                style = textStyle,
                                color = if (enabled) colors.focusedTextColor else colors.disabledTextColor,
                                textAlign = TextAlign.Center,
                            )
                        }
                        if (index != limit - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiCodeTextField() {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .clickable(
                    interactionSource = NoInteractionSource(),
                    indication = null,
                ) {
                    focusManager.clearFocus(true)
                },
        ) {
            var textValue by remember { mutableStateOf(TextFieldValue()) }
            var value by remember { mutableStateOf("") }

            SeugiCodeTextField(
                value = textValue,
                limit = 6,
                onValueChange = { newInput ->
                    val newValue = if (newInput.text.isNotBlank()) {
                        newInput.text
                            .replace(" ", "")
                            .replace(",", "")
                    } else {
                        newInput.text
                    }
                    if (newValue.length > 6) {
                        focusManager.clearFocus(true)
                        return@SeugiCodeTextField
                    }
                    textValue = textValue.copy(
                        text = newValue,
                        selection = TextRange(newValue.length),
                    )
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCodeTextField(
                value = value,
                limit = 6,
                isError = true,
                onValueChange = {
                    value = it
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCodeTextField(
                value = value,
                limit = 6,
                enabled = false,
                onValueChange = {
                    value = it
                },
            )
        }
    }
}
