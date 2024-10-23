package com.seugi.home.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.seugi.data.schedule.model.ScheduleModel
import com.seugi.designsystem.R
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCalendarCard
import com.seugi.home.HomeCard
import com.seugi.home.HomeErrorCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.model.CommonUiState
import java.time.LocalDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toKotlinLocalDate

@Composable
internal fun ScheduleCard(uiState: CommonUiState<ImmutableList<ScheduleModel>>) {
    HomeCard(
        text = "다가오는 일정",
        onClickDetail = { /*TODO*/ },
        image = painterResource(id = R.drawable.ic_calendar_line),
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    uiState.data.fastForEach { schedule ->
                        HomeCalendarCard(
                            date = "${schedule.date.monthNumber}/${schedule.date.dayOfMonth.toString().padStart(2, '0')}",
                            content = schedule.eventName,
                            dDay = "D-${-schedule.date.daysUntil(LocalDate.now().toKotlinLocalDate())}",
                        )
                    }
                }
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 일정을 확인하세요")
            }

            is CommonUiState.Error -> {
                HomeErrorCard(
                    text = "일정이 없어요",
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_emoji_happy),
                )
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
