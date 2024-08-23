package com.seugi.notificationedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seugi.data.workspace.model.WorkspacePermissionModel
import com.seugi.data.workspace.model.isAdmin
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.notificationedit.model.NotificationSideEffect
import com.seugi.ui.CollectAsSideEffect
import com.seugi.ui.shortToast

@Composable
internal fun NotificationEditScreen(
    viewModel: NotificationEditViewModel = hiltViewModel(),
    userId: Int,
    writerId: Int,
    id: Long,
    title: String,
    content: String,
    workspaceId: String,
    permission: WorkspacePermissionModel,
    onNavigationVisibleChange: (visible: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is NotificationSideEffect.Success -> {
                context.shortToast(it.message)
                popBackStack()
            }
            is NotificationSideEffect.Error -> {
                context.shortToast(it.throwable.message)
            }
        }
    }

    var titleText by remember { mutableStateOf(title) }
    var contentText by remember { mutableStateOf(content) }

    LaunchedEffect(true) {
        onNavigationVisibleChange(false)
    }

    LifecycleResumeEffect {
        onNavigationVisibleChange(false)
        onPauseOrDispose {
            onNavigationVisibleChange(true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SeugiTheme.colors.white,
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "알림 수정",
                        style = MaterialTheme.typography.titleLarge,
                        color = SeugiTheme.colors.black,
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .bounceClick(
                                onClick = {
                                    if (titleText.isEmpty()) {
                                        return@bounceClick
                                    }
                                    if (contentText.isEmpty()) {
                                        return@bounceClick
                                    }

                                    viewModel.edit(
                                        id = id,
                                        title = titleText,
                                        content = contentText,
                                    )
                                },
                                enabled = !state.isLoading,
                            ),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    vertical = 9.dp,
                                    horizontal = 12.dp,
                                ),
                            text = "완료",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SeugiTheme.colors.black,
                        )
                    }
                },
                onNavigationIconClick = popBackStack,
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(
                    horizontal = 20.dp,
                )
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            SeugiTextField(
                value = titleText,
                onValueChange = {
                    titleText = it
                },
                placeholder = "제목을 입력해 주세요",
                onClickDelete = {
                    titleText = ""
                },
                enabled = !state.isLoading,
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiTextField(
                modifier = Modifier
                    .heightIn(
                        min = 360.dp,
                    ),
                value = contentText,
                onValueChange = {
                    contentText = it
                },
                placeholder = "내용을 입력해 주세요",
                onClickDelete = {
                    contentText = ""
                },
                singleLine = false,
                enabled = !state.isLoading,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                SeugiImage(
                    modifier = Modifier
                        .size(28.dp)
                        .bounceClick(
                            onClick = {
                                viewModel.delete(
                                    id = id,
                                    workspaceId = workspaceId,
                                )
                            },
                            enabled = !state.isLoading && (
                                writerId == userId || permission.isAdmin()
                                ),
                        ),
                    resId = R.drawable.ic_trash_fill,
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                )
            }
        }
    }
}
