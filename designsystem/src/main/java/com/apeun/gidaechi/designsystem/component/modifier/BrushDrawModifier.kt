package com.apeun.gidaechi.designsystem.component.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.brushDraw(
    brush: Brush
): Modifier = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(
                brush = brush,
                blendMode = BlendMode.SrcAtop
            )
        }
    }