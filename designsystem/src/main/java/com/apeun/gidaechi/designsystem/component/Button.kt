package com.apeun.gidaechi.designsystem.component

import androidx.compose.ui.graphics.Color
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red100
import com.apeun.gidaechi.designsystem.theme.Red200
import com.apeun.gidaechi.designsystem.theme.Red300
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.Transparent
import com.apeun.gidaechi.designsystem.theme.White

sealed class ButtonType(
    val textColor: Color,
    val backgroundColor: Color,
    val disableTextColor: Color,
    val disableBackgroundColor: Color
) {
    data object Primary: ButtonType(White, Primary500, White, Primary100)
    data object Black: ButtonType(White, com.apeun.gidaechi.designsystem.theme.Black, White, Gray500)
    data object Red: ButtonType(Red500, Red200, Red300, Red100)
    data object Transparent: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, com.apeun.gidaechi.designsystem.theme.Transparent, Gray500, com.apeun.gidaechi.designsystem.theme.Transparent)
    data object Shadow: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, White, Gray500, White)
}