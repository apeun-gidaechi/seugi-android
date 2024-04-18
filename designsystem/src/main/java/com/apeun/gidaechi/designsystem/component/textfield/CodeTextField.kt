package com.apeun.gidaechi.designsystem.component.textfield

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
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray200
import com.apeun.gidaechi.designsystem.theme.Gray300
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.Transparent
import com.apeun.gidaechi.designsystem.theme.White

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
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Black,
        focusedContainerColor = White,
        focusedIndicatorColor = Transparent,
        unfocusedContainerColor = White,
        unfocusedTextColor = Black,
        unfocusedIndicatorColor = Transparent,
        disabledIndicatorColor = Transparent,
        disabledTextColor = Gray400,
        disabledContainerColor = White,
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
            cursorBrush = SolidColor(Primary500),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.clearFocus(true)
                },
            ),
            decorationBox = @Composable { innerTextField ->
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(limit) { index ->
                        Box(
                            modifier = Modifier
                                .height(52.dp)
                                .width(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        isError -> Red500
                                        isFocused && value.length == index || isFocused && limit - index == 1 && value.length == limit -> Primary500
                                        enabled -> Gray300
                                        else -> Gray200
                                    },
                                    shape = shape,
                                )
                                .padding(horizontal = 18.dp),
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
                        Spacer(modifier = Modifier.weight(1f))
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
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Black,
        focusedContainerColor = White,
        focusedIndicatorColor = Transparent,
        unfocusedContainerColor = White,
        unfocusedTextColor = Black,
        unfocusedIndicatorColor = Transparent,
        disabledIndicatorColor = Transparent,
        disabledTextColor = Gray400,
        disabledContainerColor = White,
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
            cursorBrush = SolidColor(Primary500),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.clearFocus(true)
                },
            ),
            decorationBox = @Composable { innerTextField ->
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(limit) { index ->
                        Box(
                            modifier = Modifier
                                .height(52.dp)
                                .width(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        isError -> Red500
                                        isFocused && value.text.length == index || isFocused && limit - index == 1 && value.text.length == limit -> Primary500
                                        enabled -> Gray300
                                        else -> Gray200
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
                        Spacer(modifier = Modifier.weight(1f))
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
