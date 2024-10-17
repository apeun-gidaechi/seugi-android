package com.seugi.chatdatail

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.seugi.chatdatail.model.ChatDetailSideEffect
import com.seugi.chatdatail.model.ChatLocalType
import com.seugi.common.utiles.byteToFormatString
import com.seugi.common.utiles.toAmShortString
import com.seugi.common.utiles.toFullFormatString
import com.seugi.data.core.model.UserInfoModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageType
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
import com.seugi.ui.checkFileExist
import com.seugi.ui.component.OtherProfileBottomSheet
import com.seugi.ui.downloadFile
import com.seugi.ui.getFile
import com.seugi.ui.getFileMimeType
import com.seugi.ui.getFileName
import com.seugi.ui.getFileSize
import com.seugi.ui.getMimeType
import com.seugi.ui.getUriByteArray
import com.seugi.ui.rememberKeyboardOpen
import com.seugi.ui.uriToBitmap
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    userId: Int,
    workspaceId: String = "664bdd0b9dfce726abd30462",
    isPersonal: Boolean = false,
    chatRoomId: String = "665d9ec15e65717b19a62701",
    navigateToChatDetail: (workspaceId: String, chatRoomId: String) -> Unit,
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
    val contentResolver = context.contentResolver
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

    var resendChatItem: ChatLocalType? by remember { mutableStateOf(null) }
    var isShowReSendDialog by remember { mutableStateOf(false) }

    var otherProfileState: UserModel? by remember { mutableStateOf(null) }
    var showOtherProfileBottomSheet by remember { mutableStateOf(false) }

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

    var selectedFileName by remember { mutableStateOf("") }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImagePreview by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                Log.d("TAG", "ChatDetailScreen: $uri")
                isShowUploadDialog = false
                selectedImageBitmap = contentResolver.uriToBitmap(uri)
                selectedFileName = contentResolver.getFileName(uri).toString()
                showImagePreview = true
                Log.d("TAG", "ChatDetailScreen: $selectedFileName $selectedImageBitmap")
            }
        }
    }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                isShowUploadDialog = false
                viewModel.channelSend(
                    userId = userId,
                    fileName = contentResolver.getFileName(uri).toString(),
                    fileMimeType = contentResolver.getMimeType(uri).toString(),
                    fileByteArray = contentResolver.getUriByteArray(uri),
                    fileByte = contentResolver.getFileSize(uri),
                )
            }
        }
    }

    var showSelectUrlImagePreview by remember { mutableStateOf(false) }
    var selectUrlImageItem: MessageRoomEvent.MessageParent.Img? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

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
            is ChatDetailSideEffect.SuccessMoveRoom -> {
                navigateToChatDetail(workspaceId, nowSideEffect.chatRoomId)
            }
            is ChatDetailSideEffect.FailedMoveRoom -> {
                coroutineScope.launch {
                    Toast.makeText(context, nowSideEffect.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadInfo(
            userId = userId,
            isPersonal = isPersonal,
            chatRoomId = chatRoomId,
            workspaceId = workspaceId,
        )
    }

    LifecycleStartEffect(key1 = Unit) {
        viewModel.collectStompLifecycle(userId)
        viewModel.channelReconnect(userId, chatRoomId)
        onStopOrDispose {
            viewModel.subscribeCancel()
        }
    }

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState.isOpen) {
            scrollState.animateScrollBy(with(density) { -keyboardState.height.toPx() })
        }
    }

    LaunchedEffect(key1 = scrollState.canScrollForward) {
        if (!scrollState.canScrollForward) {
            viewModel.loadMessage(
                userId = userId,
                chatRoomId = chatRoomId,
            )
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
            },
        ) {
            ResendDialog(
                onClickDelete = {
                    viewModel.deleteFailedSend(
                        uuid = resendChatItem?.uuid ?: "",
                    )
                    isShowReSendDialog = false
                },
                onClickResend = {
                    when (resendChatItem) {
                        is ChatLocalType.FailedText -> {
                            val it = resendChatItem as ChatLocalType.FailedText
                            viewModel.channelResend(
                                userId = userId,
                                content = it.text,
                                uuid = it.uuid,
                            )
                        }
                        is ChatLocalType.FailedImgSend -> {
                            val it = resendChatItem as ChatLocalType.FailedImgSend
                            viewModel.channelResend(
                                userId = userId,
                                content = "${it.image}::${it.fileName}",
                                uuid = it.uuid,
                                type = MessageType.IMG,
                            )
                        }
                        is ChatLocalType.FailedImgUpload -> {
                            val it = resendChatItem as ChatLocalType.FailedImgUpload
                            viewModel.channelReSend(
                                userId = userId,
                                image = it.image,
                                fileName = it.fileName,
                                uuid = it.uuid,
                            )
                        }
                        is ChatLocalType.FailedFileSend -> {
                            val it = resendChatItem as ChatLocalType.FailedFileSend
                            viewModel.channelResend(
                                userId = userId,
                                content = "${it.fileUrl}::${it.fileName}::${it.fileByte}",
                                uuid = it.uuid,
                                type = MessageType.FILE,
                            )
                        }
                        is ChatLocalType.FailedFileUpload -> {
                            val it = resendChatItem as ChatLocalType.FailedFileUpload
                            viewModel.channelResend(
                                userId = userId,
                                fileName = it.fileName,
                                fileMimeType = it.fileMimeType,
                                fileByteArray = it.fileByteArray,
                                fileByte = it.fileByte,
                                uuid = it.uuid,
                            )
                        }
                        else -> {}
                    }
                    isShowReSendDialog = false
                },
            )
        }
    }

    if (showOtherProfileBottomSheet) {
        OtherProfileBottomSheet(
            profile = otherProfileState?.picture,
            name = otherProfileState?.name ?: "",
            status = "",
            spot = "",
            belong = "",
            phone = "",
            wire = "",
            location = "",
            onClickChat = {
                viewModel.getPersonalChat(
                    workspaceId = workspaceId,
                    userId = otherProfileState?.id!!,
                )
            },
            onDismissRequest = {
                showOtherProfileBottomSheet = false
            },
        )
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
                        sendEnabled = text.isNotEmpty(),
                        onSendClick = {
                            viewModel.channelSend(
                                userId = userId,
                                content = text,
                                type = MessageType.MESSAGE,
                            )
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
                        adminId = state.roomInfo?.adminId,
                        members = state.roomInfo?.members ?: persistentListOf(),
                        notificationState = notificationState,
                        showLeft = !isPersonal,
                        onClickInviteMember = {},
                        onClickMember = {
                            if (it.id == userId) {
                                return@ChatSideBarScreen
                            }
                            otherProfileState = it
                            showOtherProfileBottomSheet = true
                        },
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
                items(messageQueueState.reversed()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp,
                            ),
                        horizontalAlignment = Alignment.End,
                    ) {
                        val onClickRetry: (ChatLocalType) -> Unit = {
                            resendChatItem = it
                            isShowReSendDialog = true
                        }
                        when (it) {
                            is ChatLocalType.SendText -> {
                                SeugiChatItem(
                                    type = ChatItemType.Sending(message = it.text),
                                )
                            }
                            is ChatLocalType.FailedText -> {
                                SeugiChatItem(
                                    type = ChatItemType.Failed(
                                        message = it.text,
                                        onClickRetry = { onClickRetry(it) },
                                    ),
                                )
                            }
                            is ChatLocalType.SendFile -> {
                                SeugiChatItem(
                                    type = ChatItemType.FileSending(
                                        fileName = it.fileName,
                                        fileSize = byteToFormatString(it.fileByte),
                                    ),
                                )
                            }
                            is ChatLocalType.FailedFileUpload -> {
                                SeugiChatItem(
                                    type = ChatItemType.FileFailed(
                                        onClickRetry = { onClickRetry(it) },
                                        fileName = it.fileName,
                                        fileSize = byteToFormatString(it.fileByte),
                                    ),
                                )
                            }
                            is ChatLocalType.FailedFileSend -> {
                                SeugiChatItem(
                                    type = ChatItemType.FileFailed(
                                        onClickRetry = { onClickRetry(it) },
                                        fileName = it.fileName,
                                        fileSize = byteToFormatString(it.fileByte),
                                    ),
                                )
                            }
                            is ChatLocalType.SendImg -> {
                                SeugiChatItem(
                                    type = ChatItemType.ImageSending(
                                        image = it.image.asImageBitmap(),
                                    ),
                                )
                            }
                            is ChatLocalType.FailedImgUpload -> {
                                SeugiChatItem(
                                    type = ChatItemType.ImageFailedBitmap(
                                        onClickRetry = { onClickRetry(it) },
                                        image = it.image.asImageBitmap(),
                                    ),
                                )
                            }
                            is ChatLocalType.FailedImgSend -> {
                                SeugiChatItem(
                                    type = ChatItemType.ImageFailedUrl(
                                        onClickRetry = { onClickRetry(it) },
                                        image = it.image,
                                    ),
                                )
                            }

                            is ChatLocalType.SendFileUrl -> {
                                SeugiChatItem(
                                    type = ChatItemType.FileSending(
                                        fileName = it.fileName,
                                        fileSize = byteToFormatString(it.fileByte),
                                    ),
                                )
                            }
                            is ChatLocalType.SendImgUrl -> {
                                SeugiChatItem(
                                    type = ChatItemType.ImageSendingUrl(
                                        image = it.image,
                                    ),
                                )
                            }
                        }
                    }
                }
                items(
                    state.message.filter {
                        if (searchText.isEmpty()) return@filter true
                        when (it) {
                            is MessageRoomEvent.MessageParent.Me -> {
                                it.message.contains(searchText)
                            }
                            is MessageRoomEvent.MessageParent.Other -> {
                                it.message.contains(searchText)
                            }
                            else -> false
                        }
                    }
                ) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp,
                            ),
                    ) {
                        SeugiChatItem(
                            modifier = Modifier
                                .`if`(
                                    item is MessageRoomEvent.MessageParent.Me ||
                                        (item is MessageRoomEvent.MessageParent.Img && item.userId == userId) ||
                                        (item is MessageRoomEvent.MessageParent.File && item.userId == userId),
                                ) {
                                    align(Alignment.End)
                                },
                            type = when (item) {
                                is MessageRoomEvent.MessageParent.Me -> {
                                    val readUser = item.getUserCount(state.roomInfo?.members ?: persistentListOf())
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Me(
                                        isLast = item.isLast,
                                        message = item.message,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                    )
                                }
                                is MessageRoomEvent.MessageParent.Other -> {
                                    val readUser = item.getUserCount(state.roomInfo?.members ?: persistentListOf())
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    val userInfo = state.users[item.userId]
                                    ChatItemType.Others(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        userName = userInfo?.name ?: "",
                                        userProfile = userInfo?.picture?.ifEmpty { null },
                                        message = item.message,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                    )
                                }

                                is MessageRoomEvent.MessageParent.Date ->
                                    ChatItemType.Date(item.timestamp.toFullFormatString())

                                is MessageRoomEvent.MessageParent.Img -> {
                                    Log.d("TAG", "ChatDetailScreen: $item $userId")
                                    ChatItemType.Image(
                                        onClick = {
                                            selectUrlImageItem = item
                                            showSelectUrlImagePreview = true
                                        },
                                        image = item.url,
                                        isMe = item.userId == userId,
                                    )
                                }

                                is MessageRoomEvent.MessageParent.File ->
                                    ChatItemType.File(
                                        onClick = {
                                            if (!checkFileExist(item.fileName)) {
                                                downloadFile(
                                                    context = context,
                                                    url = item.url,
                                                    name = item.fileName,
                                                )
                                            } else {
                                                val intent = Intent(Intent.ACTION_VIEW)
                                                intent.setDataAndType(
                                                    Uri.fromFile(getFile(item.fileName)),
                                                    getFileMimeType(item.fileName),
                                                )
                                                try {
                                                    launcher.launch(intent)
                                                } catch (e: Exception) {
                                                    // not open app
                                                }
                                            }
                                        },
                                        isMe = item.userId == userId,
                                        fileName = item.fileName,
                                        fileSize = byteToFormatString(item.fileSize),
                                    )

                                is MessageRoomEvent.MessageParent.Left -> ChatItemType.Else("${state.users[item.userId]?.name ?: ""}님이 방에서 퇴장하셨습니다.")

                                is MessageRoomEvent.MessageParent.Enter -> ChatItemType.Else("${state.users[item.userId]?.name ?: ""}님이 방에 입장하셨습니다.")

                                is MessageRoomEvent.MessageParent.Etc -> ChatItemType.Else(item.toString())
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
                onFileUploadClick = {
                    fileLauncher.launch("*/*")
                },
                onImageUploadClick = {
                    galleryLauncher.launch("image/*")
                },
            )
        }
        AnimatedVisibility(
            visible = showImagePreview,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            if (selectedImageBitmap != null) {
                ChatDetailImageUploadPreviewScreen(
                    imageBitmap = selectedImageBitmap!!.asImageBitmap(),
                    onClickRetry = {
                        galleryLauncher.launch("image/*")
                    },
                    onClickSend = {
                        if (selectedImageBitmap != null) {
                            showImagePreview = false
                            viewModel.channelSend(
                                userId = userId,
                                image = selectedImageBitmap!!,
                                fileName = selectedFileName,
                            )
                            selectedFileName = ""
                            selectedImageBitmap = null
                        }
                    },
                    popBackStack = {
                        showImagePreview = false
                    },
                )
            }
        }
        AnimatedVisibility(visible = showSelectUrlImagePreview) {
            if (selectUrlImageItem != null) {
                ChatDetailImagePreviewScreen(
                    image = selectUrlImageItem!!.url,
                    fileIsExist = checkFileExist(selectUrlImageItem?.fileName!!),
                    onClickDownload = {
                        val image = selectUrlImageItem!!
                        if (!checkFileExist(image.fileName)) {
                            downloadFile(
                                context = context,
                                url = image.url,
                                name = image.fileName,
                            )
                        }
                    },
                    popBackStack = {
                        selectUrlImageItem = null
                        showSelectUrlImagePreview = false
                    },
                )
            }
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
    adminId: Int?,
    members: ImmutableList<UserInfoModel>,
    notificationState: Boolean,
    showLeft: Boolean,
    onClickMember: (UserModel) -> Unit,
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
                if (showLeft) {
                    SeugiIconButton(
                        resId = R.drawable.ic_logout_line,
                        onClick = onClickLeft,
                        size = 28.dp,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = SeugiTheme.colors.gray600),
                    )
                }
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
                    enabled = false,
                )
            }
        },
        containerColor = SeugiTheme.colors.white,
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
                    userName = it.userInfo.name,
                    userProfile = it.userInfo.picture.ifEmpty { null },
                    isCrown = it.userInfo.id == adminId,
                    crownColor = SeugiTheme.colors.yellow500,
                    onClick = {
                        onClickMember(it.userInfo)
                    },
                    action = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_expand_right_line),
                            contentDescription = null,
                            tint = SeugiTheme.colors.gray500,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ChatDetailImageUploadPreviewScreen(imageBitmap: ImageBitmap, onClickRetry: () -> Unit, onClickSend: () -> Unit, popBackStack: () -> Unit) {
    val painter = BitmapPainter(imageBitmap)
    val zoomState = rememberZoomState(contentSize = painter.intrinsicSize)

    BackHandler(
        enabled = true,
        onBack = popBackStack,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.black),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(zoomState),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 13.dp)
                    .bounceClick(popBackStack),
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "뒤로가기",
                    tint = SeugiTheme.colors.white,
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .rotate(90f)
                    .size(28.dp)
                    .bounceClick(onClickSend),
                painter = painterResource(id = R.drawable.ic_send_fill),
                contentDescription = "전송하기",
                tint = SeugiTheme.colors.white,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .bounceClick(onClickRetry),
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_refresh_fill),
                contentDescription = "재시도",
                tint = SeugiTheme.colors.white,
            )
        }
    }
}

@Composable
private fun ChatDetailImagePreviewScreen(image: String, fileIsExist: Boolean, onClickDownload: () -> Unit, popBackStack: () -> Unit) {
    val painter = rememberAsyncImagePainter(model = image)
    val zoomState = rememberZoomState()
    BackHandler(
        enabled = true,
        onBack = popBackStack,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.black),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .zoomable(zoomState),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 13.dp)
                    .bounceClick(popBackStack),
            ) {
                Icon(
                    modifier = Modifier
                        .size(28.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "뒤로가기",
                    tint = SeugiTheme.colors.white,
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        if (!fileIsExist) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .bounceClick(onClickDownload),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_expand_stop_down_line),
                    contentDescription = "재시도",
                    tint = SeugiTheme.colors.white,
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
private fun ResendDialog(modifier: Modifier = Modifier, text: String = "재전송하시겠습니까?", onClickDelete: () -> Unit, onClickResend: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(16.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = text,
                style = SeugiTheme.typography.body1,
                color = SeugiTheme.colors.black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .bounceClick(onClickDelete),
                    text = "삭제",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.primary600,
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .bounceClick(onClickResend),
                    text = "재전송",
                    style = SeugiTheme.typography.subtitle2,
                    color = SeugiTheme.colors.primary600,
                )
            }
        }
    }
}
