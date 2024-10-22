package com.seugi.task.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiDatePickerDialog
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.rememberSeugiDatePickerState
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.task.create.model.TaskCreateSideEffect
import com.seugi.ui.CollectAsSideEffect
import java.time.LocalDate

@Composable
internal fun TaskCreateScreen(viewModel: TaskCreateViewModel = hiltViewModel(), popBackStack: () -> Unit, workspaceId: String) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is TaskCreateSideEffect.Failed -> {
                isLoading = false
                Toast.makeText(context, "과제 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
            TaskCreateSideEffect.Success -> popBackStack()
        }
    }

    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    val datePickerState = rememberSeugiDatePickerState()
    var date by remember { mutableStateOf(LocalDate.now()) }

    var isShowDatePicekrDialog by remember { mutableStateOf(false) }
    if (isShowDatePicekrDialog) {
        SeugiDatePickerDialog(
            state = datePickerState,
            onDismissRequest = {
                datePickerState.selectedDate = LocalDate.now()
                isShowDatePicekrDialog = false
            },
            onClickDate = { date, isValid ->
                if (isValid) {
                    datePickerState.selectedDate = date
                }
            },
            onClickSuccess = {
                date = datePickerState.selectedDate
                datePickerState.selectedDate = LocalDate.now()
                isShowDatePicekrDialog = false
            },
        )
    }
    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "과제 만들기",
                        color = SeugiTheme.colors.black,
                        style = SeugiTheme.typography.subtitle1,
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (!isLoading) SeugiTheme.colors.primary500 else SeugiTheme.colors.primary500.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(14.dp),
                            )
                            .bounceClick(
                                onClick = {
                                    isLoading = true
                                    viewModel.createTask(
                                        workspaceId = workspaceId,
                                        title = titleText,
                                        description = descriptionText,
                                        dueDate = date.atTime(0, 0, 0),
                                    )
                                },
                                enabled = !isLoading,
                            ),
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = 4.dp,
                                horizontal = 10.dp,
                            ),
                            text = "만들기",
                            color = SeugiTheme.colors.white,
                            style = SeugiTheme.typography.body1,
                        )
                    }
                },
                containerColors = SeugiTheme.colors.primary050,
                onNavigationIconClick = popBackStack,
            )
        },
        containerColor = SeugiTheme.colors.primary050,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 4.dp,
                    top = 8.dp,
                ),
                text = "제목",
                color = SeugiTheme.colors.black,
                style = SeugiTheme.typography.subtitle2,
            )

            SeugiTextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleText,
                onValueChange = {
                    titleText = it
                },
                onClickDelete = {
                    titleText = ""
                },
            )

            SeugiTextField(
                modifier = Modifier
                    .heightIn(
                        min = 265.dp,
                        max = 340.dp,
                    )
                    .fillMaxWidth(),
                value = descriptionText,
                onValueChange = {
                    descriptionText = it
                },
            )

            SeugiDatePickerButton(
                text = "${date.monthValue}월 ${date.dayOfMonth}일까지",
                onClick = {
                    isShowDatePicekrDialog = true
                },
            )
        }
    }
}

@Composable
private fun SeugiDatePickerButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = (1.5).dp,
                color = SeugiTheme.colors.gray400,
                shape = RoundedCornerShape(12.dp),
            )
            .bounceClick(onClick),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 15.dp,
                )
                .fillMaxWidth(),
        ) {
            Text(
                text = text,
                color = SeugiTheme.colors.black,
                style = SeugiTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = com.seugi.designsystem.R.drawable.ic_calendar_line),
                contentDescription = null,
                tint = SeugiTheme.colors.gray500,
            )
        }
    }
}
