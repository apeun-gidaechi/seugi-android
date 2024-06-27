package com.apeun.gidaechi.notification

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray200
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.White
import com.apeun.gidaechi.ui.changeNavigationColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotificationScreen() {
    val view = LocalView.current

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
            .background(Primary050)
            .fillMaxSize(),
    ) {
        SeugiTopBar(
            title = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "알림",
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
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            items(40) {
                Spacer(modifier = Modifier.height(8.dp))
                NotificationCard(
                    title = "교내 체육대회 안내",
                    description = "5월말 체육대회 합니다. 깔쌈하게 준비해오십시오",
                    author = "캣스기",
                    emojiList = listOf("17", "32"),
                    createdAt = "5월 3일 수요일",
                    onClickAddEmoji = { /*TODO*/ },
                    onClickDetailInfo = { /*TODO*/ },
                    onClickNotification = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
internal fun NotificationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    author: String,
    emojiList: List<String>,
    createdAt: String,
    onClickAddEmoji: () -> Unit,
    onClickDetailInfo: () -> Unit,
    onClickNotification: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp),
            )
            .bounceClick(
                onClick = onClickNotification,
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$author · $createdAt",
                    color = Gray600,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .bounceClick(
                            onClick = onClickDetailInfo,
                        ),
                    painter = painterResource(id = R.drawable.ic_detail_vertical_line),
                    contentDescription = "자세히",
                    colorFilter = ColorFilter.tint(Gray500),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Black,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = description,
                color = Black,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(28.dp)
                        .bounceClick(
                            onClick = onClickAddEmoji,
                        ),
                    painter = painterResource(id = R.drawable.ic_add_emoji),
                    contentDescription = "이모지 추가하기",
                    colorFilter = ColorFilter.tint(Gray600),
                )
                emojiList.fastForEach {
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(
                        modifier = Modifier
                            .background(
                                color = Gray100,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .border(
                                width = 1.dp,
                                color = Gray200,
                                shape = RoundedCornerShape(8.dp),
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "❤\uFE0F",
                            color = Black,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = it,
                            color = Gray600,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}
