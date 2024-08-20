package com.seugi.notificationcreate

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White
import com.seugi.notificationcreate.model.NotificationSideEffect
import com.seugi.ui.CollectAsSideEffect

@Composable
internal fun NotificationCreateScreen(
    viewModel: NotificationCreateViewModel = hiltViewModel(),
    workspaceId: String,
    onNavigationVisibleChange: (visible: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is NotificationSideEffect.Success -> {
                Toast.makeText(context, "등록에 성공하였습니다", Toast.LENGTH_SHORT).show()
                popBackStack()
            }
            is NotificationSideEffect.Error -> {
                Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

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
        containerColor = White,
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "새 알림 작성",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .bounceClick(
                                enabled = !state.isLoading,
                                onClick = {
                                    if (titleText.isEmpty()) { return@bounceClick }
                                    if (contentText.isEmpty()) { return@bounceClick }
                                    viewModel.create(
                                        title = titleText,
                                        content = contentText,
                                        workspaceId = workspaceId
                                    )
                                }
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    vertical = 9.dp,
                                    horizontal = 12.dp
                                ),
                            text = "완료",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black
                        )
                    }
                },
                onNavigationIconClick = popBackStack,
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(
                    horizontal = 20.dp
                )
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
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiTextField(
                modifier = Modifier
                    .heightIn(
                        min = 360.dp
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
                enabled = !state.isLoading
            )
        }
    }
}