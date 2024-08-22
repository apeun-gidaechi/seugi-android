package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDialog

@Composable
fun Dialog() {
    var dialogFirstShow by remember { mutableStateOf(false) }
    var dialogSecondShow by remember { mutableStateOf(false) }
    if (dialogFirstShow) {
        SeugiDialog(
            title = "제목을 입력해주세요",
            content = "본문을 입력해주세요",
            onDismissRequest = {
                dialogFirstShow = false
            },
        )
    }

    if (dialogSecondShow) {
        SeugiDialog(
            title = "제목을 입력해주세요",
            content = "본문을 입력해주세요",
            onDismissRequest = {
                dialogSecondShow = false
            },
            onLeftRequest = {
                dialogSecondShow = false
            },
            onRightRequest = {
                dialogSecondShow = false
            },
        )
    }
    Column {
        SeugiButton(
            onClick = {
                dialogFirstShow = true
            },
            type = ButtonType.Primary,
            text = "Dialog First Show",
        )
        SeugiButton(
            onClick = {
                dialogSecondShow = true
            },
            type = ButtonType.Primary,
            text = "Dialog Second Show",
        )
    }
}
