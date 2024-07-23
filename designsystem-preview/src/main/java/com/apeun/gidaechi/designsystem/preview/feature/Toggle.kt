package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seugi.designsystem.component.SeugiToggle
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun Toggle() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var checked by remember { mutableStateOf(false) }
            SeugiToggle(
                checked = checked,
                onCheckedChangeListener = {
                    checked = it
                },
            )
        }
    }
}
