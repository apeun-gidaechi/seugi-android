package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.SeugiBadge
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun Badge() {
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
