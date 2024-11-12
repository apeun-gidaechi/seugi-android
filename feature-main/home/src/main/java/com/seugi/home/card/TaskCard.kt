package com.seugi.home.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.seugi.data.assignment.model.AssignmentModel
import com.seugi.designsystem.R
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCalendarCard
import com.seugi.home.HomeCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.model.CommonUiState
import java.time.LocalDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toKotlinLocalDate

@Composable
fun TaskCard(uiState: CommonUiState<ImmutableList<AssignmentModel>>, navigateToTask: () -> Unit) {
    HomeCard(
        text = "다가오는 과제",
        onClickDetail = navigateToTask,
        image = painterResource(id = R.drawable.ic_check_fill),
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    uiState.data.fastForEach { task ->
                        if (task.dueDate == null) {
                            HomeCalendarCard(
                                date = "기한없음",
                                content = task.title,
                                dDay = "기한없음",
                            )
                        } else {
                            val dayCnt = LocalDate.now().toKotlinLocalDate().daysUntil(task.dueDate!!.date)
                            HomeCalendarCard(
                                date = "${task.dueDate!!.monthNumber}/${task.dueDate!!.dayOfMonth.toString().padStart(2, '0')}",
                                content = task.title,
                                dDay = "D" + when {
                                    dayCnt > 0 -> "-$dayCnt"
                                    dayCnt < 0 -> "+${-dayCnt}"
                                    else -> " Day"
                                },
                            )
                        }
                    }
                }
            }

            is CommonUiState.Error -> {
                HomeNotFoundText(text = "구글 계정을 등록하고 과제를 확인하세요")
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 과제를 확인하세요")
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    LoadingDotsIndicator()
                }
            }
        }
    }
}
