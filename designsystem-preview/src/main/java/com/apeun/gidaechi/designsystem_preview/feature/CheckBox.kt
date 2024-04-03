package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.CheckBoxType
import com.apeun.gidaechi.designsystem.component.SeugiCheckbox
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun CheckBox() {
    var checked by remember { mutableStateOf(true) }

    SeugiTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            SeugiCheckbox(
                type = CheckBoxType.Small,
                checked = checked,
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCheckbox(
                type = CheckBoxType.Medium,
                checked = checked,
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCheckbox(
                type = CheckBoxType.Large,
                checked = checked,
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
