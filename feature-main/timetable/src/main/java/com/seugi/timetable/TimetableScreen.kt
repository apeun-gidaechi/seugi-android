package com.seugi.timetable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme
import java.time.DayOfWeek
import java.time.LocalDate
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TimetableScreen(viewModel: TimetableViewModel = hiltViewModel(), workspaceId: String, popBackStack: () -> Unit) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(workspaceId) {
        if (workspaceId.isEmpty()) return@LaunchedEffect

        viewModel.loadTimeTable(workspaceId)
    }

    val nowDate by remember { mutableStateOf(LocalDate.now()) }
    val startDayOfWeek by remember { derivedStateOf { nowDate.with(DayOfWeek.MONDAY) } }
    val endDayOfWeek by remember { derivedStateOf { nowDate.with(DayOfWeek.SUNDAY) } }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "시간표",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black,
                    )
                },
                onNavigationIconClick = popBackStack,
                containerColors = SeugiTheme.colors.primary050,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dropShadow(DropShadowType.EvBlack1)
                    .background(
                        color = SeugiTheme.colors.white,
                        shape = RoundedCornerShape(12.dp),
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 13.5.dp),
                    text = "${startDayOfWeek.monthValue}/${startDayOfWeek.dayOfMonth}" +
                        "~${endDayOfWeek.monthValue}/${endDayOfWeek.dayOfMonth}",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.black,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(BorderStroke(1.dp, SeugiTheme.colors.gray100)),
                    )
                    persistentListOf("월", "화", "수", "목", "금").forEach {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(24.dp)
                                .border(BorderStroke(1.dp, SeugiTheme.colors.gray100)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = it,
                                style = SeugiTheme.typography.caption2,
                                color = SeugiTheme.colors.gray500,
                            )
                        }
                    }
                }
                uiState.keys.sorted().forEach { key ->
                    val value = uiState.getOrDefault(key, null)
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(24.dp)
                                .border(BorderStroke(1.dp, SeugiTheme.colors.gray100)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = key[0].toString(),
                                style = SeugiTheme.typography.caption2,
                                color = SeugiTheme.colors.gray600,
                            )
                        }

                        persistentListOf(0, 1, 2, 3, 4).forEach { index ->
                            val item = value?.getOrNull(index)
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .border(BorderStroke(1.dp, SeugiTheme.colors.gray100)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = item?.subject ?: "",
                                    style = SeugiTheme.typography.body2,
                                    color = SeugiTheme.colors.black,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
