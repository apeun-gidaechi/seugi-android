package com.seugi.home

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDialog
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.brushDraw
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.component.modifier.`if`
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.model.CommonUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToChatSeugi: () -> Unit,
    navigateToJoinWorkspace: () -> Unit,
    onNavigationVisibleChange: (Boolean) -> Unit,
    navigateToWorkspaceDetail: (String) -> Unit,
    navigateToWorkspaceCreate: () -> Unit,
) {
    val view = LocalView.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { 3 }
    val items = (1..7).toList()
    val selectIndex = 4
    val indicatorOffset by remember {
        derivedStateOf { (pagerState.currentPage * 10).dp }
    }

    val changeNavColorWhite = SeugiTheme.colors.white
    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                changeNavigationColor(window, changeNavColorWhite, false)
            }
        }
    }

    val changeNavColor = SeugiTheme.colors.primary050
    LaunchedEffect(key1 = true) {
        onNavigationVisibleChange(true)
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            changeNavigationColor(window, changeNavColor, false)
        }
    }

    if (state.showDialog) {
        SeugiDialog(
            title = "학교 등록하기",
            content = "학교를 등록한 뒤 스기를 사용할 수 있어요",
            leftText = "새 학교 만들기",
            rightText = "기존 학교 가입",
            onLeftRequest = {
                onNavigationVisibleChange(false)
                navigateToWorkspaceCreate()
            },
            onRightRequest = {
                onNavigationVisibleChange(false)
                navigateToJoinWorkspace()
            },
            onDismissRequest = {},
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(SeugiTheme.colors.primary050)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            SeugiTopBar(
                title = {
                    Text(
                        text = "홈",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black,
                    )
                },
                containerColors = Color.Transparent,
            )
        }

        item {
            HomeCard(
                text = "내 학교",
                image = painterResource(id = R.drawable.ic_school),
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
            ) {
                when (val schoolState = state.schoolState) {
                    is CommonUiState.Success -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = (6.5).dp),
                                text = schoolState.data,
                                style = SeugiTheme.typography.subtitle2,
                                color = SeugiTheme.colors.gray600,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            SeugiButton(
                                onClick = {
                                    onNavigationVisibleChange(false)
                                    navigateToWorkspaceDetail(state.nowWorkspace.first)
                                },
                                type = ButtonType.Gray,
                                text = "전환",
                            )
                        }
                    }

                    is CommonUiState.NotFound -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "내 학교를 등록해주세요",
                                style = SeugiTheme.typography.body2,
                                color = SeugiTheme.colors.gray600,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
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

        item {
            HomeCard(
                text = "오늘의 시간표",
                onClickDetail = { /*TODO*/ },
                image = painterResource(id = R.drawable.ic_book_fill),
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
            ) {
                when (val timeScheduleState = state.timeScheduleState) {
                    is CommonUiState.Success -> {
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

                                val weight = (items.size - selectIndex.toFloat()) - 1f
                                // index 계산후 weight가 음수를 넘어가지 않은 경우
                                if (weight > 0) {
                                    Spacer(modifier = Modifier.weight(weight))
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                timeScheduleState.data.fastForEachIndexed { index, subject ->
                                    HomeSubjectCard(
                                        modifier = Modifier.weight(1f),
                                        index = index,
                                        selectIndex = selectIndex,
                                        subject = subject,
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

        item {
            var nowButtonState by remember { mutableStateOf(ButtonState.Idle) }
            HomeCard(
                text = "오늘의 급식",
                image = painterResource(id = R.drawable.ic_utensils_line),
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
                onClickDetail = {},
                onChangeButtonState = {
                    nowButtonState = it
                },
            ) {
                when (val mealState = state.mealState) {
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
                                val item = when(index) {
                                    0 -> mealState.data.breakfast
                                    1 -> mealState.data.lunch
                                    else -> mealState.data.dinner
                                }
                                if (item == null) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
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
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier.background(
                                                    color = SeugiTheme.colors.primary500,
                                                    shape = RoundedCornerShape(34.dp)
                                                ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(
                                                        vertical = 4.dp,
                                                        horizontal = 10.dp
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
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = item.menu[i],
                                                    style = SeugiTheme.typography.body2,
                                                    color = SeugiTheme.colors.gray700,
                                                )
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = item.menu.getOrNull(i+1) ?: "",
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

        item {
            HomeCard(
                text = "캣스기",
                image = painterResource(id = R.drawable.ic_appicon_round),
                modifier = Modifier,
            ) {
                when (state.catSeugiState) {
                    is CommonUiState.Success -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = (1.5).dp,
                                        brush = GradientPrimary,
                                        shape = CircleShape,
                                    )
                                    .bounceClick(
                                        onClick = navigateToChatSeugi,
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    modifier = Modifier.padding(vertical = (15.5).dp),
                                    text = "2학년 4반에 아무나 한명 뽑아줘...",
                                    style = SeugiTheme.typography.subtitle2,
                                    color = SeugiTheme.colors.gray500,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    modifier = Modifier.brushDraw(GradientPrimary),
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "",
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "지난주",
                                style = SeugiTheme.typography.body2,
                                color = SeugiTheme.colors.gray600,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = SeugiTheme.colors.gray100,
                                            shape = RoundedCornerShape(4.dp),
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        modifier = Modifier.padding(vertical = (12).dp),
                                        text = "급식에 복어가 나오는 날이 언제..",
                                        style = SeugiTheme.typography.body1,
                                        color = SeugiTheme.colors.black,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "6월 21일",
                                        style = SeugiTheme.typography.body2,
                                        color = SeugiTheme.colors.gray600,
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = SeugiTheme.colors.gray100,
                                            shape = RoundedCornerShape(4.dp),
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        modifier = Modifier.padding(vertical = (12).dp),
                                        text = "우리 학교 대회 담당하는 분이 누구...",
                                        style = SeugiTheme.typography.body1,
                                        color = SeugiTheme.colors.black,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "6월 21일",
                                        style = SeugiTheme.typography.body2,
                                        color = SeugiTheme.colors.gray600,
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                }
                            }
                        }
                    }

                    is CommonUiState.NotFound -> {
                        HomeNotFoundText(text = "학교를 등록하고 캣스기와 대화해 보세요")
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

        item {
            HomeCard(
                text = "다가오는 일정",
                onClickDetail = { /*TODO*/ },
                image = painterResource(id = R.drawable.ic_calendar_line),
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
            ) {
                when (val scheduleState = state.schoolScheduleState) {
                    is CommonUiState.Success -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            scheduleState.data.fastForEachIndexed { i, data ->
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
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun changeNavigationColor(window: Window, backgroundColor: Color, isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = backgroundColor.toArgb()
        window.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

@Composable
internal fun HomeCard(modifier: Modifier = Modifier, text: String, image: Painter, colorFilter: ColorFilter? = null, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .animateContentSize()
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(32.dp)
                    .background(
                        color = SeugiTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .`if`(text == "캣스기") {
                            Modifier.background(SeugiTheme.colors.white)
                        },
                    painter = image,
                    contentDescription = "",
                    colorFilter = colorFilter,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            content()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun HomeCard(
    modifier: Modifier = Modifier,
    text: String,
    onClickDetail: () -> Unit,
    image: Painter,
    colorFilter: ColorFilter? = null,
    onChangeButtonState: (ButtonState) -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .animateContentSize()
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .bounceClick(
                    onClick = onClickDetail,
                    onChangeButtonState = onChangeButtonState,
                    requireUnconsumed = true,
                ),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(32.dp)
                    .background(
                        color = SeugiTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    painter = image,
                    contentDescription = "",
                    colorFilter = colorFilter,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_expand_right_line),
                contentDescription = "상세보기",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            content()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun HomeSubjectCard(modifier: Modifier, index: Int, selectIndex: Int, subject: String) {
    val isNowSelected = index == selectIndex
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center),
                text = (index + 1).toString(),
                style = SeugiTheme.typography.body1,
                color = if (isNowSelected) SeugiTheme.colors.primary500 else SeugiTheme.colors.primary300,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = (8.64).dp,
                    )
                    .align(Alignment.Center),
                text = subject,
                style = SeugiTheme.typography.body1,
                color = if (isNowSelected) SeugiTheme.colors.white else if (index > selectIndex) SeugiTheme.colors.primary300 else SeugiTheme.colors.primary200,
            )
        }
    }
}

@Composable
internal fun HomeCalendarCard(modifier: Modifier = Modifier, date: String, content: String, dDay: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = date,
            style = SeugiTheme.typography.body1,
            color = SeugiTheme.colors.primary500,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = content,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = dDay,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.gray600,
        )
    }
}

@Composable
internal fun HomeNotFoundText(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            text = text,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.gray600,
        )
    }
}

@Composable
internal fun HomeSchoolSelectCard(text: String, isSelect: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.bounceClick(
            onClick = onClick,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SeugiTheme.colors.primary050,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = text,
                    color = SeugiTheme.colors.black,
                    style = SeugiTheme.typography.subtitle2,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isSelect) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_setting_fill),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_expand_right_line),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                )
            }
        }
    }
}
