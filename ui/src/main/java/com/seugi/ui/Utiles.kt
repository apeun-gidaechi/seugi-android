package com.seugi.ui

import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun changeNavigationColor(window: Window, backgroundColor: Color, isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = backgroundColor.toArgb()
        window.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}


data class ExKeyboardState(
    val isOpen: Boolean = false,
    val height: Dp = 0.dp,
)

fun View.isKeyboardOpen(): Pair<Boolean, Int> {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return Pair(keypadHeight > screenHeight * 0.15, screenHeight - rect.bottom)
}

@Composable
fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val view = LocalView.current
    val density = LocalDensity.current

    fun Pair<Boolean, Int>.toState() = ExKeyboardState(
        isOpen = first,
        height = with(density) { second.toDp() - 48.dp },
    )

    return produceState(initialValue = view.isKeyboardOpen().toState()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            value = view.isKeyboardOpen().toState()
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}