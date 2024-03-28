package com.apeun.gidaechi.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.ButtonState
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red100
import com.apeun.gidaechi.designsystem.theme.Red200
import com.apeun.gidaechi.designsystem.theme.Red300
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.Transparent
import com.apeun.gidaechi.designsystem.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class ButtonType(
    val textColor: Color,
    val backgroundColor: Color,
    val disableTextColor: Color,
    val disableBackgroundColor: Color,
) {
    data object Primary : ButtonType(White, Primary500, White, Primary100)
    data object Black : ButtonType(White, com.apeun.gidaechi.designsystem.theme.Black, White, Gray500)
    data object Red : ButtonType(Red500, Red200, Red300, Red100)
    data object Transparent : ButtonType(
        com.apeun.gidaechi.designsystem.theme.Black,
        com.apeun.gidaechi.designsystem.theme.Transparent,
        Gray500,
        com.apeun.gidaechi.designsystem.theme.Transparent,
    )
    data object Shadow : ButtonType(com.apeun.gidaechi.designsystem.theme.Black, White, Gray500, White)
}

@Composable
fun SeugiFullWidthButton(
    onClick: () -> Unit,
    type: ButtonType,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = NoInteractionSource(),
) {
    val buttonModifier =
        if (type is ButtonType.Shadow) {
            Modifier
                .dropShadow(DropShadowType.Ev1)
                .background(White)
        } else {
            Modifier
        }

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val color = if (enabled) type.backgroundColor else type.disableBackgroundColor
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color.copy(alpha = 1f)
        } else {
            color.copy(alpha = 0.64f)
        },
        label = "",
    )

    Box(modifier = buttonModifier) {
        Button(
            onClick = onClick,
            modifier = buttonModifier
                .then(modifier)
                .fillMaxWidth()
                .height(54.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(buttonState) {
                    awaitPointerEventScope {
                        buttonState = if (buttonState == ButtonState.Hold) {
                            waitForUpOrCancellation()
                            ButtonState.Idle
                        } else {
                            awaitFirstDown(false)
                            ButtonState.Hold
                        }
                    }
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = animColor,
                contentColor = type.textColor,
                disabledContainerColor = animColor,
                disabledContentColor = type.disableTextColor,
            ),
            enabled = enabled,
            shape = shape,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SeugiButton(
    onClick: () -> Unit,
    type: ButtonType,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = NoInteractionSource(),
) {
    val buttonModifier =
        if (type is ButtonType.Shadow) {
            Modifier
                .dropShadow(DropShadowType.Ev1)
                .background(White)
        } else {
            Modifier
        }

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val color = if (enabled) type.backgroundColor else type.disableBackgroundColor
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color.copy(alpha = 1f)
        } else {
            color.copy(alpha = 0.64f)
        },
        label = "",
    )

    Box(modifier = buttonModifier) {
        Button(
            onClick = onClick,
            modifier = buttonModifier
                .then(modifier)
                .height(36.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(buttonState) {
                    awaitPointerEventScope {
                        buttonState = if (buttonState == ButtonState.Hold) {
                            waitForUpOrCancellation()
                            ButtonState.Idle
                        } else {
                            awaitFirstDown(false)
                            ButtonState.Hold
                        }
                    }
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = animColor,
                contentColor = type.textColor,
                disabledContainerColor = animColor,
                disabledContentColor = type.disableTextColor,
            ),
            enabled = enabled,
            shape = shape,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeugiButtonPreview() {
    SeugiTheme {
        var testState by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            coroutineScope.launch {
                testState = false
                delay(2000)
                testState = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(White)
                .verticalScroll(rememberScrollState()),
        ) {
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(20.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
            )
        }
    }
}
