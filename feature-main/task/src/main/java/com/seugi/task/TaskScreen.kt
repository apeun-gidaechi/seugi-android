package com.seugi.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.isTeacher
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.task.model.CommonUiState
import java.time.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toKotlinLocalDate

@Composable
internal fun TaskScreen(viewModel: TaskViewModel = hiltViewModel(), popBackStack: () -> Unit, workspaceId: String, profile: ProfileModel, navigateToTaskCreate: () -> Unit) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(workspaceId) {
        if (workspaceId.isEmpty()) return@LaunchedEffect

        viewModel.getClassroomTask()
        viewModel.getWorkspaceTask(workspaceId)
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "과제",
                        color = SeugiTheme.colors.black,
                        style = SeugiTheme.typography.subtitle1,
                    )
                },
                onNavigationIconClick = popBackStack,
                containerColors = SeugiTheme.colors.primary050,
            )
        },
        containerColor = SeugiTheme.colors.primary050,
        floatingActionButton = {
            if (profile.permission.isTeacher()) {
                FloatingActionButton(
                    modifier = Modifier.size(60.dp),
                    onClick = navigateToTaskCreate,
                    containerColor = SeugiTheme.colors.primary500,
                    shape = CircleShape,
                    content = {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = SeugiTheme.colors.white,
                        )
                    },
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (uiState.classroomTaskState is CommonUiState.Success) {
                item {
                    TaskTitleCard(
                        title = "구글 클래스룸 과제",
                    )
                }

                val state = uiState.classroomTaskState as CommonUiState.Success

                items(state.data) {
                    if (it.dueDate == null) {
                        TaskCard(
                            title = it.title,
                            description = it.description ?: "",
                            dDay = "기한없음",
                        )
                    } else {
                        TaskCard(
                            title = it.title,
                            description = it.description ?: "",
                            dDay = "D-${-it.dueDate!!.date.daysUntil(LocalDate.now().toKotlinLocalDate())}",
                        )
                    }
                }
            }

            if (uiState.workspaceTaskState is CommonUiState.Success) {
                item {
                    TaskTitleCard(
                        title = "일반 과제",
                    )
                }

                val state = uiState.workspaceTaskState as CommonUiState.Success

                items(state.data) {
                    if (it.dueDate == null) {
                        TaskCard(
                            title = it.title,
                            description = it.description ?: "",
                            dDay = "D-??",
                        )
                    } else {
                        TaskCard(
                            title = it.title,
                            description = it.description ?: "",
                            dDay = "D-${-it.dueDate!!.date.daysUntil(LocalDate.now().toKotlinLocalDate())}",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskTitleCard(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = Modifier.padding(
            start = 4.dp,
            top = 8.dp,
        ),
        text = title,
        color = SeugiTheme.colors.black,
        style = SeugiTheme.typography.subtitle2,
    )
}

@Composable
private fun TaskCard(modifier: Modifier = Modifier, title: String, description: String, dDay: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(DropShadowType.EvPrimary1)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = SeugiTheme.colors.black,
                    style = SeugiTheme.typography.body1,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.background(
                        color = SeugiTheme.colors.primary500,
                        shape = RoundedCornerShape(14.dp),
                    ),
                ) {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 4.dp,
                        ),
                        text = dDay,
                        color = SeugiTheme.colors.white,
                        style = SeugiTheme.typography.body1,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = SeugiTheme.colors.gray600,
                style = SeugiTheme.typography.body2,
            )
        }
    }
}
