package com.seugi.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SeugiColors(
    val yellow100: Color = Color(0xFFFFFDF7),
    val yellow200: Color = Color(0xFFFDF2CB),
    val yellow300: Color = Color(0xFFFFE999),
    val yellow500: Color = Color(0xFFF7CC33),

    val orange100: Color = Color(0xFFFFF9F7),
    val orange200: Color = Color(0xFFFDDDCB),
    val orange300: Color = Color(0xFFFFBE99),
    val orange500: Color = Color(0xFFF76E33),

    val red100: Color = Color(0xFFFFF9F7),
    val red200: Color = Color(0xFFFFE8E8),
    val red300: Color = Color(0xFFFFACAC),
    val red500: Color = Color(0xFFF90707),

    val transparent: Color = Color(0x00FFFFFF),
    val black: Color = Color(0xFF000000),
    val white: Color = Color(0xFFFFFFFF),

    val gray100: Color = Color(0xFFF4F5F9),
    val gray200: Color = Color(0xFFF1F1F1),
    val gray300: Color = Color(0xFFE6E6E6),
    val gray400: Color = Color(0xFFD1D1D1),
    val gray500: Color = Color(0xFFAAAAAA),
    val gray600: Color = Color(0xFF787878),
    val gray700: Color = Color(0xFF333333),
    val gray800: Color = Color(0xFF1D1D1D),

    val primary050: Color = Color(0xFFF8FCFF),
    val primary100: Color = Color(0xFFDCEFFF),
    val primary200: Color = Color(0xFFB1DBFD),
    val primary300: Color = Color(0xFF7EC4FC),
    val primary400: Color = Color(0xFF4BA9F5),
    val primary500: Color = Color(0xFF1D93F3),
    val primary600: Color = Color(0xFF0481E6),
    val primary700: Color = Color(0xFF0A6EC0),

    val wrapper: Color = Color(0xFF475B6B),

)

internal val LocalSeugiColor = staticCompositionLocalOf { SeugiColors() }
