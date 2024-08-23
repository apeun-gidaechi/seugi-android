package com.seugi.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
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

val Pretendard = Typography(
    displayLarge = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    displayMedium = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    headlineLarge = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    headlineMedium = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    titleLarge = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    titleMedium = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyLarge = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyMedium = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    labelLarge = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    labelMedium = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 1.3.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
)

sealed class SeugiTextTheme {
    data object DisplayLarge : SeugiTextTheme()
    data object DisplayMedium : SeugiTextTheme()
    data object HeadlineLarge : SeugiTextTheme()
    data object HeadlineMedium : SeugiTextTheme()
    data object TitleLarge : SeugiTextTheme()
    data object TitleMedium : SeugiTextTheme()
    data object BodyLarge : SeugiTextTheme()
    data object BodyMedium : SeugiTextTheme()
    data object LabelLarge : SeugiTextTheme()
    data object LabelMedium : SeugiTextTheme()

    @Composable
    fun toTextStyle() = when (this) {
        is BodyLarge -> MaterialTheme.typography.bodyLarge
        is BodyMedium -> MaterialTheme.typography.bodyMedium
        is DisplayLarge -> MaterialTheme.typography.displayLarge
        is DisplayMedium -> MaterialTheme.typography.displayMedium
        is HeadlineLarge -> MaterialTheme.typography.headlineLarge
        is HeadlineMedium -> MaterialTheme.typography.headlineMedium
        is LabelLarge -> MaterialTheme.typography.labelLarge
        is LabelMedium -> MaterialTheme.typography.labelMedium
        is TitleLarge -> MaterialTheme.typography.titleLarge
        is TitleMedium -> MaterialTheme.typography.titleMedium
    }
}

internal val LocalSeugiTypography = staticCompositionLocalOf { SeugiTypography() }
