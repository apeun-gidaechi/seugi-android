package com.seugi.notification

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.common.utiles.toTimeString
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.animation.combinedBounceClick
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray100
import com.seugi.designsystem.theme.Gray200
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.Gray600
import com.seugi.designsystem.theme.Primary050
import com.seugi.designsystem.theme.Primary100
import com.seugi.designsystem.theme.Primary300
import com.seugi.designsystem.theme.White
import com.seugi.notification.model.NotificationEmojiState
import com.seugi.notification.model.getEmojiList
import com.seugi.ui.changeNavigationColor
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    workspaceId: String,
    userId: Int
) {
    val view = LocalView.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = {
            viewModel.enabledRefresh()
            viewModel.refreshFirstPage(workspaceId)
        },
    )
    val lazyListState = rememberLazyListState()
    var isShowPopupDialog by remember { mutableStateOf(false) }

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

    LaunchedEffect(key1 = lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward) {
            viewModel.nextPage(workspaceId)
        }
    }

    if (isShowPopupDialog) {
        NotificationPopupDialog(
            onDismissRequest = {
                isShowPopupDialog = false
            },
            onClickEdit = {
                isShowPopupDialog = false
            },
            onClickDeclaration = {
                isShowPopupDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.background(Primary050),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    actions = {
                        SeugiImage(
                            modifier = Modifier
                                .size(28.dp)
                                .bounceClick(
                                    onClick = {

                                    }
                                ),
                            resId = R.drawable.ic_write_line,
                            colorFilter = ColorFilter.tint(Black)
                        )
                    },
                    containerColors = Color.Transparent,
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState),
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .background(Primary050)
                    .fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                AnimatedVisibility(
                    visible = state.notices.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NotificationNotFound()
                }

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    state = lazyListState
                ) {
                    items(state.notices) {
                        Spacer(modifier = Modifier.height(8.dp))
                        NotificationCard(
                            title = it.title,
                            description = it.content,
                            author = it.userName,
                            emojiList = it.getEmojiList(userId),
                            createdAt = it.creationDate.toTimeString(),
                            onClickEmoji = { /*TODO*/ },
                            onClickDetailInfo = {
                                isShowPopupDialog = true
                            },
                            onClickNotification = { /*TODO*/ },
                            onLongClick = {
                                isShowPopupDialog = true
                            }
                        )
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isRefresh,
                state = pullRefreshState,
            )
        }
    }
}

@Composable
fun NotificationNotFound() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 12.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SeugiImage(
                modifier = Modifier.size(64.dp),
                resId = R.drawable.ic_emoji_mouth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "알림이 없어요",
                color = Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun NotificationPopupDialog(
    onDismissRequest: () -> Unit,
    onClickEdit: () -> Unit,
    onClickDeclaration: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            color = White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .bounceClick(
                                onClick = onClickEdit
                            ),
                        text = "알림 수정",
                        color = Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .bounceClick(
                                onClick = onClickDeclaration
                            ),
                        text = "알림 신고",
                        color = Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun NotificationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    author: String,
    emojiList: ImmutableList<NotificationEmojiState>,
    createdAt: String,
    onClickEmoji: (emoji: String) -> Unit,
    onClickDetailInfo: () -> Unit,
    onClickNotification: () -> Unit,
    onLongClick: () -> Unit
) {
    Box(
        modifier = modifier.combinedBounceClick(
            onClick = onClickNotification,
            onLongClick = onLongClick
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(DropShadowType.EvBlack1)
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp),
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
                FlowRow(
                    modifier = Modifier
                ) {
                    emojiList.fastForEach {
                        NotificationEmoji(
                            emoji = it.emoji,
                            count = it.count,
                            isChecked = it.isMe,
                            onClick = {
                                onClickEmoji(it.emoji)
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationEmoji(
    modifier: Modifier = Modifier,
    emoji: String,
    count: Int,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.bounceClick(onClick)
    ) {
        Row(
            modifier = modifier
                .background(
                    color = if (isChecked) Primary100 else Gray100,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = if (isChecked) Primary300 else Gray200,
                    shape = RoundedCornerShape(8.dp),
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(
                    vertical = 4.dp
                ),
                text = emoji,
                color = Black,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = count.toString(),
                color = Gray600,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}