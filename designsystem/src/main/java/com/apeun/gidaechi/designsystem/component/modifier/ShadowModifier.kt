package com.apeun.gidaechi.designsystem.component.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class DropShadowType(
    val blur: Dp,
    val offsetY: Dp,
    val color: Color
) {
    data object Ev1: DropShadowType(9.dp, 3.dp, Color(0x08000000))
    data object Ev2: DropShadowType(12.dp, 4.dp, Color(0x0F000000))
    data object Ev3: DropShadowType(18.dp, 6.dp, Color(0x14000000))
}