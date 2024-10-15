package com.seugi.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.theme.SeugiTheme

/**
 * Seugi Category
 *
 * @param modifier the Modifier to be applied to this badge.
 * @param category the means the category to be seen.
 * @param isChoose the indicates whether the status is selected.
 * @param onClick An event occurs when the category is pressed.
 */
@Composable
fun SeugiCategory(modifier: Modifier = Modifier, category: String, isChoose: Boolean, onClick: () -> Unit = {}) {
    val animBackgroundColor by animateColorAsState(
        targetValue = when {
            isChoose -> SeugiTheme.colors.primary500
            !isChoose -> SeugiTheme.colors.gray100.copy(alpha = 0.7f)
            else -> SeugiTheme.colors.gray100
        },
        label = "",
    )
    val animTextColor by animateColorAsState(
        targetValue = if (isChoose) SeugiTheme.colors.white else SeugiTheme.colors.gray500,
        label = "",
    )

    Surface(
        modifier = modifier
            .bounceClick(
                onClick = onClick,
            ),
        shape = RoundedCornerShape(17.dp),
        color = animBackgroundColor,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
            text = category,
            color = animTextColor,
            style = SeugiTheme.typography.subtitle2,
        )
    }
}

@Preview
@Composable
private fun PreviewSeugiCategory() {
    var isChoose by remember { mutableStateOf(false) }
    val onClick: () -> Unit = {
        isChoose = !isChoose
    }
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiCategory(
                category = "어쩔티비",
                isChoose = isChoose,
                onClick = onClick,
            )
            SeugiCategory(
                category = "어쩔티비1",
                isChoose = !isChoose,
                onClick = onClick,

            )
            SeugiCategory(
                category = "어쩔티비2",
                isChoose = isChoose,
                onClick = onClick,
            )
        }
    }
}
