package com.seugi.designsystem.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.theme.SeugiTheme

/**
 * Seugi TextField
 *
 * @param modifier the Modifier to be applied to this text field
 * @param value the means the value to be seen.
 * @param onValueChange the event is forwarded when the value changes.
 * @param onClickDelete the event occurs when you try to delete value.
 * @param placeholder the indicates the value to be displayed before the value is entered.
 * @param enabled the whether TextField can be entered.
 * @param singleLine the whether it is possible to enter only one line.
 * @param textStyle the setting for when the value is displayed.
 * @param shape the setting for how round the textField is.
 * @param colors specifies the color of the text field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeugiTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClickDelete: () -> Unit,
    placeholder: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
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
    val animBorderColor by animateColorAsState(
        targetValue = if (isFocused) SeugiTheme.colors.primary500 else SeugiTheme.colors.gray400,
        label = "",
    )

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            value = value,
            modifier = modifier
                .height(52.dp)
                .fillMaxWidth()
                .background(
                    color = if (enabled) colors.focusedContainerColor else colors.disabledContainerColor,
                    shape = shape,
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .border(
                    width = (1.5).dp,
                    color = animBorderColor,
                    shape = shape,
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = textStyle,
            singleLine = singleLine,
            cursorBrush = SolidColor(SeugiTheme.colors.primary500),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = if (enabled) SeugiTheme.colors.gray500 else SeugiTheme.colors.gray400,
                        )
                    },
                    label = null,
                    trailingIcon = {
                        if (value.isNotEmpty()) {
                            SeugiIconButton(
                                resId = R.drawable.ic_close_fill,
                                onClick = onClickDelete,
                                enabled = enabled,
                                colors = IconButtonColors(
                                    containerColor = colors.focusedContainerColor,
                                    disabledContainerColor = colors.disabledContainerColor,
                                    contentColor = SeugiTheme.colors.gray500,
                                    disabledContentColor = colors.disabledTextColor,
                                ),
                            )
                        }
                    },
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(start = 16.dp, end = 16.dp),
                    shape = shape,
                    enabled = enabled,
                    colors = colors,
                    interactionSource = NoInteractionSource(),
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiTextField() {
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
            var value by remember { mutableStateOf("") }
            SeugiTextField(
                value = value,
                onValueChange = {
                    value = it
                },
                onClickDelete = {
                    value = ""
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiTextField(
                value = value,
                onValueChange = {
                    value = it
                },
                onClickDelete = {
                    value = ""
                },
                enabled = false,
            )
        }
    }
}
