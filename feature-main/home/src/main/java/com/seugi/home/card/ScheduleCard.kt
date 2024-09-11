package com.seugi.home.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.seugi.designsystem.R
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCalendarCard
import com.seugi.home.HomeCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.model.CommonUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ScheduleCard(uiState: CommonUiState<ImmutableList<Triple<String, String, String>>>) {
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
                ) {
                    uiState.data.fastForEachIndexed { i, data ->
                        HomeCalendarCard(date = data.first, content = data.second, dDay = data.third)
                        if (i != 2) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 일정을 확인하세요")
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
