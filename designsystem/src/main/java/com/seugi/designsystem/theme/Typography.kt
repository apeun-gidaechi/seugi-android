package com.seugi.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.seugi.designsystem.R

val PretendardFontFamily = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
)

private val defaultFontStyle = TextStyle(
    fontFamily = PretendardFontFamily,
    lineHeight = 1.3.em,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
)

@Immutable
data class SeugiTypography(
    val display1: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
    ),
    val display2: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),

    val title1: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    val title2: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),

    val subtitle1: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    val subtitle2: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),

    val body1: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),
    val body2: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    val caption1: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    ),
    val caption2: TextStyle = defaultFontStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
)

internal val LocalSeugiTypography = staticCompositionLocalOf { SeugiTypography() }
