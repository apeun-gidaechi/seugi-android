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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White

@Composable
internal fun NotificationEditScreen(
    viewModel: NotificationEditViewModel = hiltViewModel(),
    id: Long,
    onNavigationVisibleChange: (visible: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        onNavigationVisibleChange(false)
        viewModel.load(id)
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
                        text = "알림 수정",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .bounceClick(
                                onClick = {

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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            SeugiTextField(
                value = state.title,
                onValueChange = viewModel::setTitle,
                placeholder = "제목을 입력해 주세요",
                onClickDelete = {
                    viewModel.setTitle("")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiTextField(
                modifier = Modifier
                    .heightIn(
                        min = 360.dp
                    ),
                value = state.content,
                onValueChange = viewModel::setContent,
                placeholder = "내용을 입력해 주세요",
                onClickDelete = {
                    viewModel.setContent("")
                },
                singleLine = false
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SeugiImage(
                    modifier = Modifier
                        .size(28.dp)
                        .bounceClick(
                            onClick = {}
                        ),
                    resId = R.drawable.ic_trash_fill,
                    colorFilter = ColorFilter.tint(Gray500)
                )
            }
        }
    }
}

@Composable
@Preview
private fun NotificationEditScreenPreview() {
    SeugiTheme {
        NotificationEditScreen(
            viewModel = viewModel(),
            id = 0,
            onNavigationVisibleChange = {},
            popBackStack = {}
        )
    }
}