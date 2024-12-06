package com.seugi.workspacedetail.feature.settingalarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiToggle
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme

@Composable
internal fun SettingAlarmScreen(viewModel: SettingAlarmViewModel = hiltViewModel(), workspaceId: String, popBackStack: () -> Unit) {
    val alarmState by viewModel.alarmState.collectAsStateWithLifecycle()

    LaunchedEffect(workspaceId) {
        viewModel.load(workspaceId)
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "알림 설정",
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
                            viewModel.changeAlarmState(
                                alarmState = !alarmState,
                                workspaceId = workspaceId,
                            )
                        },
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.5.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "전체 알림 허용",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.black,
                )
                Spacer(modifier = Modifier.weight(1f))
                SeugiToggle(
                    checked = alarmState,
                    onCheckedChangeListener = {
                        viewModel.changeAlarmState(
                            alarmState = it,
                            workspaceId = workspaceId,
                        )
                    },
                )
            }
        }
    }
}
