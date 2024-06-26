package com.apeun.gidaechi.home

import android.app.Activity
import android.graphics.Color.toArgb
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.SeugiButton
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary200
import com.apeun.gidaechi.designsystem.theme.Primary300
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    val view = LocalView.current
    val items = (1..7).toList()
    val selectIndex = 6

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
                    color = Black
                )
            },
            colors = TopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.Transparent,
                titleContentColor = Black,
                actionIconContentColor = Color.Transparent
            )
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "내 학교",
                image = {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_school),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Gray600)
                    )
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = (6.5).dp),
                        text = "대구 소프트웨어 마이스터 고등학교",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray600
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SeugiButton(
                        onClick = { /*TODO*/ },
                        type = ButtonType.Gray,
                        text = "전환"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HomeCard(
                text = "오늘의 시간표",
                onClickDetail = { /*TODO*/ },
                image = {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_book_fill),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Gray600)
                    )
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(34.dp)
                            .background(
                                color = Primary100,
                                shape = RoundedCornerShape(23.dp)
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(34.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(selectIndex.toFloat() + 0.9f)
                                .fillMaxHeight()
                                .background(
                                    color = Primary500,
                                    shape = RoundedCornerShape(23.dp)
                                )
                        )

                        val weight = (items.size - selectIndex.toFloat()) - 1f
                        // index 계산후 weight가 음수를 넘어가지 않은 경우
                        if (weight > 0) {
                            Spacer(modifier = Modifier.weight(weight))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items.forEachIndexed { index, itme ->
                            HomeSubjectCard(
                                modifier = Modifier.weight(1f),
                                index = index,
                                selectIndex = selectIndex,
                                subject = "안드"
                            )
                        }
                    }
                }
            }
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
        window.decorView.systemUiVisibility = if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

@Composable
internal fun HomeCard(
    modifier: Modifier = Modifier,
    text: String,
    image: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .padding(vertical = 4.dp)
                    .background(
                        color = Gray100,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                image()
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = Black
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.padding(horizontal = 12.dp)
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
    image: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .bounceClick(
                onClick = onClickDetail
            )
    ) {
        Column(
            modifier = modifier
                .dropShadow(DropShadowType.EvBlack1)
                .background(
                    color = White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(vertical = 4.dp)
                        .background(
                            color = Gray100,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    image()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_expand_right_line),
                    contentDescription = "상세보기",
                    colorFilter = ColorFilter.tint(Gray500)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                content()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
internal fun HomeSubjectCard(
    modifier: Modifier,
    index: Int,
    selectIndex: Int,
    subject: String,
) {
    val isNowSelected = index == selectIndex
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center),
                text = (index+1).toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isNowSelected) Primary500 else Primary300
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = (8.64).dp
                    )
                    .align(Alignment.Center),
                text = subject,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isNowSelected) White else if (index > selectIndex) Primary300 else Primary200
            )
        }
    }
}