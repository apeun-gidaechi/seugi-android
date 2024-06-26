package com.apeun.gidaechi.home

import android.app.Activity
import android.graphics.Color.toArgb
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
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
import androidx.lifecycle.compose.LifecycleResumeEffect
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.ButtonState
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.GradientPrimary
import com.apeun.gidaechi.designsystem.component.SeugiButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.brushDraw
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray300
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Gray700
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary200
import com.apeun.gidaechi.designsystem.theme.Primary300
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen() {
    val view = LocalView.current
    val pagerState = rememberPagerState { 3 }
    val items = (1..7).toList()
    val selectIndex = 4
    val indicatorOffset by remember {
        derivedStateOf { (pagerState.currentPage * 10).dp }
    }

    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                changeNavigationColor(window, White, false)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            changeNavigationColor(window, Primary050, false)
        }
    }

    Column(
        modifier = Modifier
            .animateContentSize()
            .verticalScroll(rememberScrollState())
            .background(Primary050)
            .fillMaxSize(),
    ) {
        SeugiTopBar(
            title = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "홈",
                    style = MaterialTheme.typography.titleLarge,
                    color = Black,
                )
            },
            colors = TopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.Transparent,
                titleContentColor = Black,
                actionIconContentColor = Color.Transparent,
            ),
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "내 학교",
                image = painterResource(id = R.drawable.ic_school),
                colorFilter = ColorFilter.tint(Gray600),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = (6.5).dp),
                        text = "대구 소프트웨어 마이스터 고등학교",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray600,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SeugiButton(
                        onClick = { /*TODO*/ },
                        type = ButtonType.Gray,
                        text = "전환",
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "오늘의 시간표",
                onClickDetail = { /*TODO*/ },
                image = painterResource(id = R.drawable.ic_book_fill),
                colorFilter = ColorFilter.tint(Gray600),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(34.dp)
                            .background(
                                color = Primary100,
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
                                    color = Primary500,
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
                        items.fastForEachIndexed { index, itme ->
                            HomeSubjectCard(
                                modifier = Modifier.weight(1f),
                                index = index,
                                selectIndex = selectIndex,
                                subject = "안드",
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            var nowButtonState by remember { mutableStateOf(ButtonState.Idle) }
            HomeCard(
                text = "오늘의 급식",
                image = painterResource(id = R.drawable.ic_utensils_line),
                colorFilter = ColorFilter.tint(Gray600),
                onClickDetail = {},
                onChangeButtonState = {
                    nowButtonState = it
                },
            ) {
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
                        Row {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = (6.5).dp),
                                    text = "오리훈제볶음밥\n간장두조림\n배추김치\n초코첵스시리얼+우유\n오렌지",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Gray700,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Primary500,
                                            shape = RoundedCornerShape(34.dp),
                                        ),
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            vertical = 4.dp,
                                            horizontal = 8.dp,
                                        ),
                                        text = "872Kcal",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = White,
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier.weight(1f),
                            ) {
                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .size(94.dp),
                                        painter = painterResource(
                                            id = when (index) {
                                                0 -> R.drawable.ic_apple
                                                1 -> R.drawable.ic_taco
                                                else -> R.drawable.ic_chicken
                                            },
                                        ),
                                        contentDescription = "",
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        text = when (index) {
                                            0 -> "아침"
                                            1 -> "점심"
                                            else -> "저녁"
                                        },
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Gray500,
                                    )
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
                                    color = Gray300,
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
                                    color = Primary500,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "캣스기",
                image = painterResource(id = R.drawable.ic_appicon_round),
            ) {
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
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            modifier = Modifier.padding(vertical = (15.5).dp),
                            text = "2학년 4반에 아무나 한명 뽑아줘...",
                            style = MaterialTheme.typography.titleMedium,
                            color = Gray500,
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray600,
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
                                    color = Gray100,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.padding(vertical = (12).dp),
                                text = "급식에 복어가 나오는 날이 언제..",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Black,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "6월 21일",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray600,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Gray100,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.padding(vertical = (12).dp),
                                text = "우리 학교 대회 담당하는 분이 누구...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Black,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "6월 21일",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray600,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "다가오는 일정",
                onClickDetail = { /*TODO*/ },
                image = painterResource(id = R.drawable.ic_calendar_line),
                colorFilter = ColorFilter.tint(Gray600),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    HomeCalendarCard(date = "7/21", content = "체육대회", dDay = "D-3")
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeCalendarCard(date = "7/23", content = "여름 교내 해커톤", dDay = "D-5")
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeCalendarCard(date = "7/25", content = "KBS 촬영", dDay = "D-7")
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
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
        window.decorView.systemUiVisibility = if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

@Composable
internal fun HomeCard(modifier: Modifier = Modifier, text: String, image: Painter, colorFilter: ColorFilter? = null, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = White,
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
                        color = Gray100,
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
                style = MaterialTheme.typography.titleMedium,
                color = Black,
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
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = White,
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
                        color = Gray100,
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
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_expand_right_line),
                contentDescription = "상세보기",
                colorFilter = ColorFilter.tint(Gray500),
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
                style = MaterialTheme.typography.bodyLarge,
                color = if (isNowSelected) Primary500 else Primary300,
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
                style = MaterialTheme.typography.bodyLarge,
                color = if (isNowSelected) White else if (index > selectIndex) Primary300 else Primary200,
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
            style = MaterialTheme.typography.bodyLarge,
            color = Primary500,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = Black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = dDay,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray600,
        )
    }
}
