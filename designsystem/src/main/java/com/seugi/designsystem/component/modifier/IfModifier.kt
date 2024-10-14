package com.seugi.designsystem.component.modifier

import androidx.compose.ui.Modifier

inline fun Modifier.`if`(enabled: Boolean, modifier: Modifier.() -> Modifier) = if (enabled) modifier() else this
