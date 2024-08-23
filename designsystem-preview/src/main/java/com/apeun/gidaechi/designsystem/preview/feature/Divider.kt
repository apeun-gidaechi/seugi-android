package com.seugi.designsystem.preview.feature

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
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun Divider() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.white),
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
