package com.apeun.gidaechi.designsystem.component

import androidx.compose.ui.graphics.Color
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red200
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.White

sealed class ButtonType(
    val textColor: Color,
    val backgroundColor: Color
) {
    data object Primary: ButtonType(White, Primary500)
    data object Black: ButtonType(White, com.apeun.gidaechi.designsystem.theme.Black)
    data object Red: ButtonType(Red500, Red200)
    data object Transparent: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, White)
    data object Shadow: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, White)
}