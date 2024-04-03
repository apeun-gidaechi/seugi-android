package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.apeun.gidaechi.designsystem.component.SeugiMemberList
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun MemberList() {
    var checked by remember { mutableStateOf(true) }
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiMemberList(
                userName = "노영재",
                userProfile = null,
                onClick = {
                },
            )
            SeugiMemberList(
                userName = "노영재",
                userProfile = null,
                checked = checked,
                onCheckedChangeListener = {
                    checked = it
                },
            )
            SeugiMemberList(
                text = "멤버 초대하기",
                onClick = {
                },
            )
        }
    }
}