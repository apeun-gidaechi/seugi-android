package com.apeun.gidaechi.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.apeun.gidaechi.designsystem.animation.ButtonState
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

@Composable
fun SeugiCategory(modifier: Modifier = Modifier, category: String, isChoose: Boolean, onClick: () -> Unit = {}) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }

    val animBackgroundColor by animateColorAsState(
        targetValue = when {
            isChoose && buttonState == ButtonState.Idle -> Primary500
            isChoose && buttonState == ButtonState.Hold -> Primary500.copy(alpha = 0.7f)
            !isChoose && buttonState == ButtonState.Hold -> Gray100.copy(alpha = 0.7f)
            else -> Gray100
        },
        label = "",
    )
    val animTextColor by animateColorAsState(
        targetValue = if (isChoose) White else Gray500,
        label = "",
    )

    Surface(
        modifier = modifier
            .bounceClick(
                onClick = onClick,
                onChangeButtonState = {
                    buttonState = it
                },
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
            style = MaterialTheme.typography.titleMedium,
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
