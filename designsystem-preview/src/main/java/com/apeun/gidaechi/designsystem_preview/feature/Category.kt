package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.apeun.gidaechi.designsystem.component.SeugiCategory
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun Category() {
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
