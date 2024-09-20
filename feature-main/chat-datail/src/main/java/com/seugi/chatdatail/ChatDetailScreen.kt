package com.seugi.chatdatail

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.chatdatail.model.ChatDetailChatTypeState
import com.seugi.chatdatail.model.ChatDetailSideEffect
import com.seugi.chatdatail.model.ChatLocalType
import com.seugi.common.utiles.toAmShortString
import com.seugi.common.utiles.toFullFormatString
import com.seugi.data.message.model.message.MessageUserModel
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.DragState
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiImage
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiRightSideScaffold
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.chat.ChatItemType
import com.seugi.designsystem.component.chat.SeugiChatItem
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.component.modifier.`if`
import com.seugi.designsystem.component.textfield.SeugiChatTextField
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.ui.addFocusCleaner
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    userId: Int,
    workspace: String = "664bdd0b9dfce726abd30462",
    isPersonal: Boolean = false,
    chatRoomId: String = "665d9ec15e65717b19a62701",
    onNavigationVisibleChange: (Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val messageQueueState by viewModel.messageSaveQueueState.collectAsStateWithLifecycle()
    val sideEffect: ChatDetailSideEffect? by viewModel.sideEffect.collectAsStateWithLifecycle(initialValue = null)
    val scrollState = rememberLazyListState()
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var notificationState by remember { mutableStateOf(false) }
    var isSearch by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardState by rememberKeyboardOpen()

    val density = LocalDensity.current
    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
    val screenSizePx = with(density) { screenSizeDp.toPx() }

    var isOpenSidebar by remember { mutableStateOf(false) }
    var isShowUploadDialog by remember { mutableStateOf(false) }
    val anchors = remember {
        DraggableAnchors {
            DragState.START at 0f
            DragState.END at screenSizePx
        }
    }

    var resendChatItem: ChatLocalType.Failed? by remember { mutableStateOf(null) }
    var isShowReSendDialog by remember { mutableStateOf(false) }

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

    LaunchedEffect(key1 = sideEffect) {
        if (sideEffect == null) {
            return@LaunchedEffect
        }
        when (val nowSideEffect = sideEffect!!) {
            is ChatDetailSideEffect.SuccessLeft -> {
                popBackStack()
            }
            is ChatDetailSideEffect.FailedLeft -> {
                coroutineScope.launch {
                    Toast.makeText(context, nowSideEffect.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        onNavigationVisibleChange(false)
        viewModel.loadInfo(
            userId = userId,
            isPersonal = isPersonal,
            chatRoomId = chatRoomId,
            workspaceId = workspace,
        )
    }

    LifecycleResumeEffect(key1 = Unit) {
        onNavigationVisibleChange(false)

        viewModel.collectStompLifecycle()
        viewModel.channelReconnect()
        onPauseOrDispose {
            viewModel.subscribeCancel()
            onNavigationVisibleChange(true)
        }
    }

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState.isOpen) {
            scrollState.animateScrollBy(with(density) { -keyboardState.height.toPx() })
        }
    }

    LaunchedEffect(key1 = scrollState.canScrollForward) {
        if (!scrollState.canScrollForward) {
            viewModel.nextPage()
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


    if (isShowReSendDialog) {
        Dialog(
            onDismissRequest = {
                isShowReSendDialog = false
            }
        ) {
            ResendDialog(
                onClickDelete = {
                    viewModel.deleteFailedSend(
                        content = resendChatItem?.text?: "",
                        uuid = resendChatItem?.uuid?: ""
                    )
                    isShowReSendDialog = false
                },
                onClickResend = {
                    viewModel.channelResend(
                        content = resendChatItem?.text?: "",
                        uuid = resendChatItem?.uuid?: ""
                    )
                    isShowReSendDialog = false
                }
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.primary050)
            .focusable(),
    ) {
        SeugiRightSideScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.primary050)
                .addFocusCleaner(
                    focusManager = focusManager,
                ),
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
                                style = SeugiTheme.typography.subtitle1,
                                color = SeugiTheme.colors.black,
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
                        }
                    },
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
                            viewModel.channelSend(text)
                            text = ""
                        },
                        onAddClick = {
                            isShowUploadDialog = true
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
                        onClickLeft = {
                            if (!isPersonal) {
                                viewModel.leftRoom(chatRoomId)
                            }
                        },
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
                    .background(SeugiTheme.colors.primary050),
                contentPadding = PaddingValues(
                    horizontal = 8.dp,
                ),
                state = scrollState,
                reverseLayout = true,
            ) {
                items(messageQueueState) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp,
                            ),
                        horizontalAlignment = Alignment.End
                    ) {
                        when (it) {
                            is ChatLocalType.Send -> {
                                SeugiChatItem(
                                    type = ChatItemType.Sending(message = it.text),
                                )
                            }
                            is ChatLocalType.Failed -> {
                                SeugiChatItem(
                                    type = ChatItemType.Failed(message = it.text),
                                    onDateClick = {
                                        resendChatItem = it
                                        isShowReSendDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
                items(state.message) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp,
                            ),
                    ) {
                        SeugiChatItem(
                            modifier = Modifier
                                .`if`(item.isMe) {
                                    align(Alignment.End)
                                },
                            type = when (item.type) {
                                ChatDetailChatTypeState.DATE -> ChatItemType.Date(item.timestamp.toFullFormatString())

                                ChatDetailChatTypeState.MESSAGE -> {
                                    val count =
                                        (state.roomInfo?.members?.size ?: 0) - item.read.size
                                    if (item.isMe) {
                                        ChatItemType.Me(
                                            isLast = item.isLast,
                                            message = item.message,
                                            createdAt = item.timestamp.toAmShortString(),
                                            count = if (count <= 0) null else count,
                                        )
                                    } else {
                                        ChatItemType.Others(
                                            isFirst = item.isFirst,
                                            isLast = item.isLast,
                                            userName = state.users.get(item.author.id)?.name ?: "",
                                            userProfile = null,
                                            message = item.message,
                                            createdAt = item.timestamp.toAmShortString(),
                                            count = if (count <= 0) null else count,
                                        )
                                    }
                                }

                                ChatDetailChatTypeState.AI -> ChatItemType.Else(item.toString())
                                ChatDetailChatTypeState.LEFT -> ChatItemType.Else("${state.users[item.author.id]?.name ?: ""}님이 방에서 퇴장하셨습니다.")
                                ChatDetailChatTypeState.ENTER -> ChatItemType.Else("${state.users[item.author.id]?.name ?: ""}님이 방에서 입장하셨습니다.")
                            },
                        )
                    }
                }
            }
        }
        if (isShowUploadDialog) {
            ChatUploadDialog(
                focusRequester = focusRequester,
                onDismissRequest = {
                    isShowUploadDialog = false
                    Log.d("TAG", "ChatDetailScreen: dismiss")
                },
                onFileUploadClick = {},
                onImageUploadClick = {},
            )
        }
    }
}

@Composable
private fun BoxScope.ChatUploadDialog(focusRequester: FocusRequester, onDismissRequest: () -> Unit, onFileUploadClick: () -> Unit, onImageUploadClick: () -> Unit) {
    var isFirst by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }
    Box(
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusProperties { canFocus = true }
            .onFocusChanged {
                Log.d("TAG", "ChatUploadDialog: ${it.isFocused}")
                if (!it.isFocused && isFirst) {
                    isFirst = false
                    return@onFocusChanged
                }
                if (!it.isFocused) {
                    isFirst = true
                    onDismissRequest()
                }
            }
            .focusable()
            .align(Alignment.BottomStart)
            .padding(
                start = 26.dp,
                bottom = 30.dp,
            )
            .dropShadow(DropShadowType.EvBlack3)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(16.dp),
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            ChatUploadDialogItem(
                resId = R.drawable.ic_file_line,
                title = "파일 업로드",
                onClick = onFileUploadClick,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChatUploadDialogItem(
                resId = R.drawable.ic_image,
                title = "이미지 업로드",
                onClick = onImageUploadClick,
            )
        }
    }
}

@Composable
private fun ChatUploadDialogItem(@DrawableRes resId: Int, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(188.dp)
            .bounceClick(
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SeugiImage(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(24.dp),
            resId = resId,
            colorFilter = ColorFilter.tint(SeugiTheme.colors.black),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = SeugiTheme.typography.subtitle2,
            color = SeugiTheme.colors.black,
        )
    }
}

@Composable
private fun ChatSideBarScreen(
    members: ImmutableList<MessageUserModel>,
    notificationState: Boolean,
    onClickMember: (MessageUserModel) -> Unit,
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
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.black,
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
                    colors = IconButtonDefaults.iconButtonColors(contentColor = SeugiTheme.colors.gray600),
                )
                Spacer(modifier = Modifier.weight(1f))
                SeugiIconButton(
                    resId = if (notificationState) R.drawable.ic_notification_fill else R.drawable.ic_notification_disabled_fill,
                    onClick = {
                        onClickNotification()
                    },
                    size = 28.dp,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = SeugiTheme.colors.gray600),
                )
                Spacer(modifier = Modifier.width(16.dp))
                SeugiIconButton(
                    resId = R.drawable.ic_setting_fill,
                    onClick = onClickSetting,
                    size = 28.dp,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = SeugiTheme.colors.gray600),
                )
            }
        },
        containerColor = SeugiTheme.colors.white
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(SeugiTheme.colors.white),
        ) {
            item {
                SeugiMemberList(
                    text = "멤버 초대하기",
                    onClick = onClickInviteMember,
                )
            }
            items(members) {
                SeugiMemberList(
                    userName = it.name,
                    userProfile = it.profile,
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
        textStyle = SeugiTheme.typography.subtitle1,
        enabled = enabled,
        cursorBrush = SolidColor(SeugiTheme.colors.primary500),
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
                        color = if (enabled) SeugiTheme.colors.gray500 else SeugiTheme.colors.gray400,
                        style = SeugiTheme.typography.subtitle1,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun ResendDialog(
    modifier: Modifier = Modifier,
    text: String = "재전송하시겠습니까?",
    onClickDelete: () -> Unit,
    onClickResend: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                style = SeugiTheme.typography.body1,
                color = SeugiTheme.colors.black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .bounceClick(onClickDelete),
                    text = "삭제",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.primary600
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .bounceClick(onClickResend),
                    text = "재전송",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.primary600
                )
            }
        }
    }
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
