package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.theme.Gray200
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

enum class DividerType {
    WIDTH,
    HEIGHT,
}

@Composable
fun SeugiDivider(modifier: Modifier = Modifier, size: Dp = 1.dp, type: DividerType, color: Color = Gray200) {
    val typeModifier =
        if (type == DividerType.WIDTH) {
            Modifier
                .fillMaxWidth()
                .height(size)
        } else {
            Modifier
                .fillMaxHeight()
                .width(size)
        }
    Box(
        modifier = modifier
            .background(color)
            .then(typeModifier),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiDivider() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            SeugiDivider(type = DividerType.WIDTH)
            Spacer(modifier = Modifier.height(10.dp))
            SeugiDivider(
                type = DividerType.WIDTH,
                size = 8.dp,
            )
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                SeugiDivider(type = DividerType.HEIGHT)
                Spacer(modifier = Modifier.width(10.dp))
                SeugiDivider(
                    type = DividerType.HEIGHT,
                    size = 8.dp,
                )
            }
        }
    }
}
