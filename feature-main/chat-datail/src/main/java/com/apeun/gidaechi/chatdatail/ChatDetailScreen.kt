package com.apeun.gidaechi.chatdatail

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.chatdatail.model.TestUserModel
import com.apeun.gidaechi.common.utiles.toAmShortString
import com.apeun.gidaechi.common.utiles.toFullFormatString
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.DragState
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.component.SeugiIconButton
import com.apeun.gidaechi.designsystem.component.SeugiMemberList
import com.apeun.gidaechi.designsystem.component.SeugiRightSideScaffold
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.chat.ChatItemType
import com.apeun.gidaechi.designsystem.component.chat.SeugiChatItem
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.component.textfield.SeugiChatTextField
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.Primary500
import java.time.Duration
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChatDetailScreen(viewModel: ChatDetailViewModel = hiltViewModel(), onNavigationVisibleChange: (Boolean) -> Unit, popBackStack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var notificationState by remember { mutableStateOf(false) }
    var isSearch by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardState by rememberKeyboardOpen()
    var isFirst by remember { mutableStateOf(true) }

    val density = LocalDensity.current
    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
    val screenSizePx = with(density) { screenSizeDp.toPx() }

    var isOpenSidebar by remember { mutableStateOf(false) }
    val anchors = remember {
        DraggableAnchors {
            DragState.START at 0f
            DragState.END at screenSizePx
        }
    }
    val anchoredState = remember {
        AnchoredDraggableState(
            initialValue = DragState.START,
            animationSpec = tween(),
            positionalThreshold = {
                0f
            },
            velocityThreshold = {
                0f
            },
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.testLoad()
        onNavigationVisibleChange(false)
    }

    LifecycleResumeEffect(key1 = Unit) {
        onNavigationVisibleChange(false)
        viewModel.testReconnect()
        onPauseOrDispose {
            viewModel.testSubscribeCancel()
            onNavigationVisibleChange(true)
        }
    }

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState.isOpen) {
            scrollState.animateScrollBy(with(density) { keyboardState.height.toPx() })
        }
    }

    LaunchedEffect(key1 = state) {
        if (isFirst) {
            coroutineScope.launch {
                if (state.message.lastIndex != -1) {
                    isFirst = !isFirst
                    scrollState.scrollToItem(state.message.lastIndex)
                }
            }
        }
    }

    BackHandler(
        enabled = isSearch || isOpenSidebar,
    ) {
        isSearch = false
        if (isOpenSidebar) {
            coroutineScope.launch {
                anchoredState.animateTo(DragState.END)
            }
            isOpenSidebar = false
        }
    }

    SideEffect {
        anchoredState.updateAnchors(anchors, DragState.END)
    }

    SeugiRightSideScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary050),
        topBar = {
            SeugiTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    if (isSearch) {
                        ChatDetailTextField(
                            searchText = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            enabled = true,
                            placeholder = "메세지 검색",
                            onDone = {
                                searchText = ""
                                isSearch = false
                            },
                        )
                    } else {
                        Text(
                            text = state.roomInfo?.roomName ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
                actions = {
                    if (!isSearch) {
                        SeugiIconButton(
                            resId = R.drawable.ic_search,
                            size = 28.dp,
                            onClick = {
                                isSearch = true
                                searchText = ""
                            },
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        SeugiIconButton(
                            resId = R.drawable.ic_hamburger_horizontal_line,
                            size = 28.dp,
                            onClick = {
                                isOpenSidebar = true
                                coroutineScope.launch {
                                    anchoredState.animateTo(DragState.START)
                                }
                            },
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                backIconCheck = true,
                shadow = true,
                onNavigationIconClick = {
                    if (isSearch) {
                        isSearch = !isSearch
                    } else {
                        popBackStack()
                    }
                },
            )
        },
        bottomBar = {
            Column {
                SeugiChatTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                        )
                        .dropShadow(DropShadowType.EvBlack1),
                    value = text,
                    placeholder = "메세지 보내기",
                    onValueChange = {
                        text = it
                    },
                    onSendClick = {
                        viewModel.testSend(text)
                        text = ""
                        coroutineScope.launch {
                            if (state.message.lastIndex != -1) {
                                scrollState.animateScrollToItem(state.message.size - 1)
                            }
                        }
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        sideBar = {
            Row {
                SeugiDivider(type = DividerType.HEIGHT)
                ChatSideBarScreen(
                    members = state.roomInfo?.members ?: persistentListOf(),
                    notificationState = notificationState,
                    onClickInviteMember = {},
                    onClickMember = {},
                    onClickLeft = {},
                    onClickNotification = {
                        notificationState = !notificationState
                    },
                    onClickSetting = { },
                )
            }
        },
        onSideBarClose = {
            coroutineScope.launch {
                anchoredState.animateTo(DragState.END)
                isOpenSidebar = false
            }
        },
        startPadding = 62.dp,
        state = anchoredState,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Primary050),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
            ),
            state = scrollState,
        ) {
            items(state.message.size) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 8.dp,
                        ),
                ) {
                    val formerItem = state.message.getOrNull(index - 1)
                    val nextItem = state.message.getOrNull(index + 1)
                    val item = state.message[index]

                    val isMe = item.userId == state.userInfo?.id
                    val isLast = item.userId != nextItem?.userId ||
                        (
                            Duration.between(
                                item.createdAt,
                                nextItem.createdAt,
                            ).seconds >= 86400 && item.userId == nextItem.userId
                            )

                    var isFirst = item.userId != formerItem?.userId
                    if (formerItem != null && Duration.between(
                            formerItem.createdAt,
                            item.createdAt,
                        ).seconds >= 86400
                    ) {
                        isFirst = true
                        Spacer(modifier = Modifier.height(12.dp))
                        SeugiChatItem(type = ChatItemType.Date(item.createdAt.toFullFormatString()))
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    val alignModifier =
                        if (isMe) Modifier.align(Alignment.End) else Modifier

                    SeugiChatItem(
                        modifier = Modifier
                            .then(alignModifier),
                        type = when {
                            isMe -> ChatItemType.Me(
                                isLast = isLast,
                                message = item.message,
                                createdAt = item.createdAt.toAmShortString(),
                                count = 1,
                            )

                            else -> ChatItemType.Others(
                                isFirst = isFirst,
                                isLast = isLast,
                                userName = item.userName,
                                userProfile = null,
                                message = item.message,
                                createdAt = item.createdAt.toAmShortString(),
                                count = 1,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatSideBarScreen(
    members: ImmutableList<TestUserModel>,
    notificationState: Boolean,
    onClickMember: (TestUserModel) -> Unit,
    onClickInviteMember: () -> Unit,
    onClickLeft: () -> Unit,
    onClickNotification: () -> Unit,
    onClickSetting: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = (9.5).dp,
                            bottom = (9.5).dp,
                        ),
                    text = "멤버",
                    style = MaterialTheme.typography.titleMedium,
                    color = Black,
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(39.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SeugiIconButton(
                    resId = R.drawable.ic_logout_line,
                    onClick = onClickLeft,
                    size = 28.dp,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Gray600),
                )
                Spacer(modifier = Modifier.weight(1f))
                SeugiIconButton(
                    resId = if (notificationState) R.drawable.ic_notification_fill else R.drawable.ic_notification_disabled_fill,
                    onClick = {
                        onClickNotification()
                    },
                    size = 28.dp,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Gray600),
                )
                Spacer(modifier = Modifier.width(16.dp))
                SeugiIconButton(
                    resId = R.drawable.ic_setting_fill,
                    onClick = onClickSetting,
                    size = 28.dp,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Gray600),
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            item {
                SeugiMemberList(
                    text = "멤버 초대하기",
                    onClick = onClickInviteMember,
                )
            }
            items(members) {
                SeugiMemberList(
                    userName = it.userName,
                    userProfile = it.userProfile,
                    onClick = {
                        onClickMember(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun ChatDetailTextField(searchText: String, onValueChange: (String) -> Unit, placeholder: String = "", enabled: Boolean = true, onDone: () -> Unit) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        value = searchText,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.titleLarge,
        enabled = enabled,
        cursorBrush = SolidColor(Primary500),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
                focusManager.clearFocus()
            },
        ),
        singleLine = true,
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box {
                if (searchText.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = if (enabled) Gray500 else Gray400,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                innerTextField()
            }
        },
    )
}

data class ExKeyboardState(
    val isOpen: Boolean = false,
    val height: Dp = 0.dp,
)

internal fun View.isKeyboardOpen(): Pair<Boolean, Int> {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    Log.d("TAG", "isKeyboardOpen: $screenHeight, $keypadHeight")
    return Pair(keypadHeight > screenHeight * 0.15, screenHeight - rect.bottom)
}

@Composable
internal fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val view = LocalView.current
    val density = LocalDensity.current

    fun Pair<Boolean, Int>.toState() = ExKeyboardState(
        isOpen = first,
        height = with(density) { second.toDp() - 48.dp },
    )

    return produceState(initialValue = view.isKeyboardOpen().toState()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            value = view.isKeyboardOpen().toState()
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
