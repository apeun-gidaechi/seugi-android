package com.seugi.home.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.seugi.designsystem.R
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.HomeSubjectCard
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.TimeScheduleUiState
import java.time.LocalTime

@Composable
internal fun TimeScheduleCard(
    uiState: CommonUiState<TimeScheduleUiState>,
    onClickDetail: () -> Unit
) {
    HomeCard(
        text = "오늘의 시간표",
        onClickDetail = onClickDetail,
        image = painterResource(id = R.drawable.ic_book_fill),
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                var nowTime by remember { mutableStateOf(LocalTime.now()) }
                val timeScheduleUiState = uiState.data
                val selectIndex: Int by remember {
                    derivedStateOf {
                        run {
                            val totalInterval =
                                timeScheduleUiState.timeSize + timeScheduleUiState.freeTimeSize
                            val duration = java.time.Duration.between(
                                timeScheduleUiState.startTime,
                                nowTime,
                            ).toMinutes()
                            (duration / totalInterval).toInt()
                        }
                    }
                }
                LaunchedEffect(true) {
                    nowTime = LocalTime.now()
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(34.dp)
                            .background(
                                color = SeugiTheme.colors.primary100,
                                shape = RoundedCornerShape(23.dp),
                            ),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(34.dp)
                            .align(Alignment.BottomStart),
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(selectIndex.toFloat() + 0.9f)
                                .fillMaxHeight()
                                .background(
                                    color = SeugiTheme.colors.primary500,
                                    shape = RoundedCornerShape(23.dp),
                                ),
                        )

                        val weight = (timeScheduleUiState.data.size - selectIndex.toFloat()) - 1f
                        // index 계산후 weight가 음수를 넘어가지 않은 경우
                        if (weight > 0) {
                            Spacer(modifier = Modifier.weight(weight))
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        timeScheduleUiState.data.fastForEachIndexed { index, subject ->
                            HomeSubjectCard(
                                modifier = Modifier.weight(1f),
                                index = index,
                                selectIndex = selectIndex,
                                subject = subject.subject,
                            )
                        }
                    }
                }
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 시간표를 확인하세요")
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