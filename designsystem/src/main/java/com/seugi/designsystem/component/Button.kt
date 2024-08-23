@file:OptIn(ExperimentalAssetLoader::class)

package com.seugi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import app.rive.runtime.kotlin.core.Rive
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface ButtonType {
    data object Primary : ButtonType
    data object Black : ButtonType
    data object Red : ButtonType
    data object Transparent : ButtonType
    data object Shadow : ButtonType
    data object Gray : ButtonType
}

/**
 * Seugi Full Width Button
 *
 * **This Button Need Rive Initialize**
 *
 * @param onClick An event occurs when the button is pressed.
 * @param type the means Button Color And Animation Color
 * @param text the indicates text to be displayed on the button.
 * @param modifier the Modifier to be applied to this Button.
 * @param enabled the indicates whether the button is activated.
 * @param isLoading the indicates whether it is currently loading.
 * @param shape means the roundness of the button.
 * @param contentPadding the indicates the spacing of the contents of the button.
 * @param interactionSource the MutableInteractionSource representing the stream of Interactions for this button. You can create and pass in your own remembered instance to observe Interactions and customize the appearance / behavior of this button in different states.
 */
@Composable
fun SeugiFullWidthButton(
    onClick: () -> Unit,
    type: ButtonType,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = NoInteractionSource(),
) {
    val buttonModifier =
        if (type is ButtonType.Shadow) {
            Modifier
                .dropShadow(DropShadowType.EvBlack1)
                .background(SeugiTheme.colors.white)
        } else {
            Modifier
        }
    val isEnabled = enabled && !isLoading

    val buttonColor = type.colors()
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val color = if (isEnabled) buttonColor.backgroundColor else buttonColor.disableBackgroundColor
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
                contentColor = buttonColor.textColor,
                disabledContainerColor = animColor,
                disabledContentColor = buttonColor.disableTextColor,
            ),
            enabled = isEnabled,
            shape = shape,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) {
            if (isLoading) {
                RiveAnimation(
                    resId = R.raw.loading_dots,
                    contentDescription = "loading gif",
                    autoplay = true,
                    animationName = buttonColor.animName,
                )
            } else {
                Text(
                    text = text,
                    style = SeugiTheme.typography.subtitle2,
                )
            }
        }
    }
}

/**
 * Seugi Button
 *
 * **This Button Need Rive Initialize**
 *
 * @param onClick An event occurs when the button is pressed.
 * @param type the means Button Color And Animation Color
 * @param text the indicates text to be displayed on the button.
 * @param modifier the Modifier to be applied to this Button.
 * @param enabled the indicates whether the button is activated.
 * @param isLoading the indicates whether it is currently loading.
 * @param shape means the roundness of the button.
 * @param contentPadding the indicates the spacing of the contents of the button.
 * @param interactionSource the MutableInteractionSource representing the stream of Interactions for this button. You can create and pass in your own remembered instance to observe Interactions and customize the appearance / behavior of this button in different states.
 */
@Composable
fun SeugiButton(
    onClick: () -> Unit,
    type: ButtonType,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = NoInteractionSource(),
) {
    val buttonModifier =
        if (type is ButtonType.Shadow) {
            Modifier
                .dropShadow(DropShadowType.EvBlack1)
                .background(SeugiTheme.colors.white)
        } else {
            Modifier
        }
    val isEnabled = enabled && !isLoading

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val buttonColor = type.colors()
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val color = if (isEnabled) buttonColor.backgroundColor else buttonColor.disableBackgroundColor
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
                contentColor = buttonColor.textColor,
                disabledContainerColor = animColor,
                disabledContentColor = buttonColor.disableTextColor,
            ),
            enabled = isEnabled,
            shape = shape,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
        ) {
            Box {
                if (isLoading) {
                    RiveAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        resId = R.raw.loading_dots,
                        contentDescription = "loading gif",
                        autoplay = true,
                        animationName = buttonColor.animName,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .align(Alignment.Center)
                        .alpha(if (isLoading) 0f else 1f),
                    text = text,
                    style = SeugiTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun SeugiIconButton(
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentDescription: String = "",
    size: Dp = 24.dp,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val animColor by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        label = "",
    )
    Box(
        modifier = modifier
            .size(size)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = size / 2,
                ),
            ),
    ) {
        Image(
            modifier = Modifier.size(size),
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(animColor),
        )
    }
}

@Immutable
data class ButtonColor(
    val textColor: Color,
    val backgroundColor: Color,
    val disableTextColor: Color,
    val disableBackgroundColor: Color,
    val animName: String,
)

@Composable
private fun ButtonType.colors() = when (this) {
    is ButtonType.Primary -> ButtonColor(SeugiTheme.colors.white, SeugiTheme.colors.primary500, SeugiTheme.colors.white, SeugiTheme.colors.primary200, "Loading_White")
    is ButtonType.Black -> ButtonColor(SeugiTheme.colors.white, SeugiTheme.colors.black, SeugiTheme.colors.white, SeugiTheme.colors.gray600, "Loading_White")
    is ButtonType.Gray -> ButtonColor(SeugiTheme.colors.gray600, SeugiTheme.colors.gray100, SeugiTheme.colors.gray500, SeugiTheme.colors.gray100, "Loading_Gray")
    is ButtonType.Red -> ButtonColor(SeugiTheme.colors.red500, SeugiTheme.colors.red200, SeugiTheme.colors.red300, SeugiTheme.colors.red200, "Loading_White")
    is ButtonType.Shadow -> ButtonColor(SeugiTheme.colors.black, SeugiTheme.colors.white, SeugiTheme.colors.gray500, SeugiTheme.colors.white, "Loading_Gray")
    is ButtonType.Transparent -> ButtonColor(
        SeugiTheme.colors.black,
        SeugiTheme.colors.transparent,
        SeugiTheme.colors.gray500,
        SeugiTheme.colors.transparent,
        "Loading_Gray",
    )
}

@Preview(showBackground = true)
@Composable
private fun SeugiButtonPreview() {
    val context = LocalContext.current
    Rive.init(context)
    SeugiTheme {
        var testState by remember { mutableStateOf(true) }
        var loadingState by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            coroutineScope.launch {
                testState = false
                loadingState = true
                delay(2000)
                loadingState = false
                testState = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(SeugiTheme.colors.white)
                .verticalScroll(rememberScrollState()),
        ) {
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                isLoading = loadingState,
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Gray,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(20.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                isLoading = loadingState,
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Gray,
                text = "시작하기",
                isLoading = loadingState,
            )
        }
    }
}
