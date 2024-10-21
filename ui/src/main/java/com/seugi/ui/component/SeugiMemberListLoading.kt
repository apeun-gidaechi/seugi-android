package com.seugi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.shimmerEffectBrush

@Composable
fun SeugiMemberListLoading(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        brush = shimmerEffectBrush(),
                        shape = CircleShape,
                    ),
            )
            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(21.dp)
                    .background(
                        brush = shimmerEffectBrush(),
                        shape = RoundedCornerShape(12.dp),
                    ),
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
