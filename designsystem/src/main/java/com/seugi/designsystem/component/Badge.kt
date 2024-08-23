package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.theme.SeugiTheme

/**
 * Seugi Badge
 *
 * @param modifier the Modifier to be applied to this badge
 * @param count the means the count to be seen.
 */
@Composable
fun SeugiBadge(modifier: Modifier = Modifier, count: Int? = null) {
    if (count == null) {
        Box(
            modifier = modifier
                .size(12.dp)
                .background(
                    color = SeugiTheme.colors.yellow100,
                    shape = CircleShape,
                ),
        )
    } else {
        Box(
            modifier = modifier
                .background(
                    color = SeugiTheme.colors.yellow100,
                    shape = RoundedCornerShape(10.dp),
                ),
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 2.dp,
                ),
                text = if (count > 300) "300+" else count.toString(),
                color = SeugiTheme.colors.white,
                style = SeugiTheme.typography.caption1,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSeugiBadge() {
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiBadge()
            Spacer(modifier = Modifier.height(10.dp))
            SeugiBadge(count = 72)
            Spacer(modifier = Modifier.height(10.dp))
            SeugiBadge(count = 400)
        }
    }
}
