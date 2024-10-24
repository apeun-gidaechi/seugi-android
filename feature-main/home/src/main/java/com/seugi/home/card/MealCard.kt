package com.seugi.home.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.MealUiState
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MealCard(uiState: CommonUiState<MealUiState>, onClickDetail: () -> Unit) {
    val pagerState = rememberPagerState { 3 }

    LaunchedEffect(true) {
        val nowTime = LocalTime.now()
        val page = when {
            nowTime <= LocalTime.of(8, 20) -> 0
            nowTime <= LocalTime.of(13, 30) -> 1
            else -> 2
        }
        pagerState.animateScrollToPage(page)
    }

    val indicatorOffset by remember {
        derivedStateOf { (pagerState.currentPage * 10).dp }
    }
    HomeCard(
        text = "오늘의 급식",
        image = painterResource(id = R.drawable.ic_utensils_line),
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
        onClickDetail = onClickDetail,
        blockNav = false,
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                Column {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = NoInteractionSource(),
                                indication = null,
                                onClick = {},
                            )
                            .pointerInput(Unit) { },
                        state = pagerState,
                    ) { index ->
                        val item = when (index) {
                            0 -> uiState.data.breakfast
                            1 -> uiState.data.lunch
                            else -> uiState.data.dinner
                        }
                        if (item == null) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                SeugiImage(
                                    modifier = Modifier.size(64.dp),
                                    resId = R.drawable.ic_emoji_sad,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "급식이 없어요",
                                    style = SeugiTheme.typography.subtitle2,
                                    color = SeugiTheme.colors.black,
                                )
                            }
                        } else {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Box(
                                        modifier = Modifier.background(
                                            color = SeugiTheme.colors.primary500,
                                            shape = RoundedCornerShape(34.dp),
                                        ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(
                                                vertical = 4.dp,
                                                horizontal = 10.dp,
                                            ),
                                            text = when (index) {
                                                0 -> "아침"
                                                1 -> "점심"
                                                else -> "저녁"
                                            },
                                            style = SeugiTheme.typography.caption1,
                                            color = SeugiTheme.colors.white,
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = item.calorie,
                                        style = SeugiTheme.typography.caption1,
                                        color = SeugiTheme.colors.gray500,
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                for (i in item.menu.indices step 2) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = item.menu[i],
                                            style = SeugiTheme.typography.body2,
                                            color = SeugiTheme.colors.gray700,
                                        )
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = item.menu.getOrNull(i + 1) ?: "",
                                            style = SeugiTheme.typography.body2,
                                            color = SeugiTheme.colors.gray700,
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(6.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = SeugiTheme.colors.gray300,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = indicatorOffset)
                                .width(16.dp)
                                .fillMaxHeight()
                                .background(
                                    color = SeugiTheme.colors.primary500,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                        )
                    }
                }
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 급식을 확인하세요")
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
