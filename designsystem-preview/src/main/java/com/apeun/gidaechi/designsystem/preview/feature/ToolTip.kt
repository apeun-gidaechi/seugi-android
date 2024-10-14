package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.SeugiToolTip
import com.seugi.designsystem.component.ToolTipType

@Composable
fun ToolTip() {
    Column {
        SeugiToolTip(
            text = "가입 수락을 대기중이에요",
            type = ToolTipType.Side,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiToolTip(
            text = "가입 수락을 대기중이에요",
            type = ToolTipType.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiToolTip(
            text = "답장",
            type = ToolTipType.SideSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiToolTip(
            text = "답장",
            type = ToolTipType.CenterSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
