package com.seugi.workspacedetail.feature.settinggeneral

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiDialog
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.workspacecdetail.R

@Composable
internal fun SettingGeneralScreen(popBackStack: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        SeugiDialog(
            title = "탈퇴 실패 안내",
            content = "시연 모드에서는 탈퇴가 불가능합니다.",
            onDismissRequest = {
                showDialog = false
            },
        )
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "일반",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black,
                    )
                },
                onNavigationIconClick = popBackStack,
            )
        },
        containerColor = SeugiTheme.colors.white,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .bounceClick(
                        onClick = {
                            showDialog = true
                        },
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "학교 나가기",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.red500,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = com.seugi.designsystem.R.drawable.ic_expand_right_line),
                    contentDescription = null,
                    tint = SeugiTheme.colors.gray400,
                )
            }
        }
    }
}
