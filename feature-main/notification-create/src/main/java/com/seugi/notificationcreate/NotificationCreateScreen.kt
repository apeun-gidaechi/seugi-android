package com.seugi.notificationcreate

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCreateScreen(
    popBackStack: () -> Unit
) {

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

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
                }
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
                singleLine = false
            )
        }
    }
}

@Preview
@Composable
private fun NotificationCreateScreenPreview() {
    SeugiTheme {
        NotificationCreateScreen {

        }
    }
}