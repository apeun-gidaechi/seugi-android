package com.seugi.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SeugiTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val statusBarColor = SeugiTheme.colors.primary600
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalSeugiColor provides SeugiTheme.colors,
        LocalSeugiTypography provides SeugiTheme.typography,
        content = content,
    )
}

object SeugiTheme {

    val colors: SeugiColors
        @ReadOnlyComposable
        @Composable
        get() = LocalSeugiColor.current

    val typography: SeugiTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalSeugiTypography.current
}
