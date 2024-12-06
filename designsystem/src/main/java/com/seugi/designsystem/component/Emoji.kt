package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiEmoji(modifier: Modifier = Modifier, emoji: String, count: Int, isChecked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.bounceClick(onClick),
    ) {
        Row(
            modifier = modifier
                .background(
                    color = if (isChecked) SeugiTheme.colors.primary100 else SeugiTheme.colors.gray100,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = if (isChecked) SeugiTheme.colors.primary300 else SeugiTheme.colors.gray200,
                    shape = RoundedCornerShape(8.dp),
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(
                    vertical = 4.dp,
                ),
                text = emoji,
                color = SeugiTheme.colors.black,
                style = SeugiTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = count.toString(),
                color = SeugiTheme.colors.gray600,
                style = SeugiTheme.typography.body1,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
