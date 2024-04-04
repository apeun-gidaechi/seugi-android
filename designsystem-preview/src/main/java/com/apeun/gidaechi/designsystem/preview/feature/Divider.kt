package com.apeun.gidaechi.designsystem.preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

@Composable
fun Divider() {
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
