package com.seugi.designsystem.component

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingDotsIndicator(count: Int = 3, pointColor: Color = SeugiTheme.colors.white, color: Color = SeugiTheme.colors.gray400, delay: Long = 150) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(count) {
            val animColor = remember { Animatable(pointColor) }

            LaunchedEffect(Unit) {
                delay(delay * (it + 1))
                launch {
                    animColor.animateTo(
                        color,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 500,
                                easing = LinearEasing,
                            ),
                            repeatMode = RepeatMode.Reverse,
                        ),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(animColor.value, CircleShape),
            )
        }
    }
}
