package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiToggle(modifier: Modifier = Modifier, checked: Boolean, onCheckedChangeListener: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChangeListener,
        colors = SwitchDefaults.colors(
            checkedThumbColor = SeugiTheme.colors.white,
            checkedTrackColor = SeugiTheme.colors.primary500,
            uncheckedThumbColor = SeugiTheme.colors.white,
            uncheckedTrackColor = SeugiTheme.colors.gray200,
            uncheckedBorderColor = SeugiTheme.colors.transparent,

        ),
        modifier = modifier
            .height(31.dp)
            .width(51.dp),
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(27.dp)
                    .background(
                        color = SeugiTheme.colors.white,
                        shape = CircleShape,
                    )
                    .dropShadow(DropShadowType.EvBlack2),
            )
        },
    )
}

@Composable
@Preview
private fun SeugiTogglePreview() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.black),
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
