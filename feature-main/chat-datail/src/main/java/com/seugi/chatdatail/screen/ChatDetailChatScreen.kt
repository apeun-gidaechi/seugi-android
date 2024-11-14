package com.seugi.chatdatail.screen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.AnchoredDraggableState
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seugi.chatdatail.ChatDetailImagePreviewScreen
import com.seugi.chatdatail.ChatDetailImageUploadPreviewScreen
import com.seugi.chatdatail.ChatDetailTextField
import com.seugi.chatdatail.ChatDetailViewModel
import com.seugi.chatdatail.ChatSideBarScreen
import com.seugi.chatdatail.ChatUploadDialog
import com.seugi.chatdatail.getUserCount
import com.seugi.chatdatail.model.ChatDetailUiState
import com.seugi.chatdatail.model.ChatLocalType
import com.seugi.common.utiles.byteToFormatString
import com.seugi.common.utiles.toAmShortString
import com.seugi.common.utiles.toFullFormatString
import com.seugi.data.core.model.UserModel
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageType
import com.seugi.designsystem.R
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.DragState
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiIconButton
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
import com.seugi.ui.downloadFile
import com.seugi.ui.getFile
import com.seugi.ui.getFileMimeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ChatDetailChatScreen(
    viewModel: ChatDetailViewModel,
    state: ChatDetailUiState,
    focusManager: FocusManager,
    coroutineScope: CoroutineScope,
    context: Context,
    fileLauncher: ManagedActivityResultLauncher<String, Uri?>,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    anchoredState: AnchoredDraggableState<DragState>,
    focusRequester: FocusRequester,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    scrollState: LazyListState,
    userId: Long,
    chatRoomId: String,
    isPersonal: Boolean,
    isSearch: Boolean,
    searchText: String,
    isOpenSidebar: Boolean,
    isShowUploadDialog: Boolean,
    text: String,
    notificationState: Boolean,
    messageQueueState: ImmutableList<ChatLocalType>,
    showImagePreview: Boolean,
    selectedImageBitmap: Bitmap?,
    showSelectUrlImagePreview: Boolean,
    selectedFileName: String,
    selectUrlImageItem: MessageRoomEvent.MessageParent.Img?,
    resendChatItem: ChatLocalType?,
    isShowReSendDialog: Boolean,
    popBackStack: () -> Unit,
    onTextChange: (String) -> Unit,
    onClickInviteMember: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onIsSearchChange: (Boolean) -> Unit,
    onsOtherProfileStateChange: (UserModel) -> Unit,
    onIsOpenSidebarChange: (Boolean) -> Unit,
    onIsShowUploadDialogChange: (Boolean) -> Unit,
    onShowOtherProfileBottomSheetChange: (Boolean) -> Unit,
    onNotificationStateChange: (Boolean) -> Unit,
    onIsShowReSendDialog: (Boolean) -> Unit,
    onResendChatItemChange: (ChatLocalType) -> Unit,
    onSelectUrlImageItemChange: (MessageRoomEvent.MessageParent.Img?) -> Unit,
    onShowSelectUrlImagePreview: (Boolean) -> Unit,
    onShowImagePreview: (Boolean) -> Unit,
    onSelectedFileName: (String) -> Unit,
    onSelectedImageBitmap: (Bitmap?) -> Unit,
    onChatLongClick: (text: String) -> Unit,
) {
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
                                onValueChange = onSearchTextChange,
                                enabled = true,
                                placeholder = "메세지 검색",
                                onDone = {
                                    onSearchTextChange("")
                                    onIsSearchChange(false)
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
                                    onIsSearchChange(true)
                                    onSearchTextChange("")
                                },
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            SeugiIconButton(
                                resId = R.drawable.ic_hamburger_horizontal_line,
                                size = 28.dp,
                                onClick = {
                                    onIsOpenSidebarChange(true)
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
                            onIsSearchChange(false)
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
                        onValueChange = onTextChange,
                        sendEnabled = text.isNotEmpty(),
                        onSendClick = {
                            val mentionList = mutableListOf<Long>()

                            if (text.startsWith("스기야 ")) {
                                mentionList.add(-1)
                            }
                            viewModel.channelSend(
                                userId = userId,
                                content = text,
                                type = MessageType.MESSAGE,
                                mention = mentionList,
                            )
                            onTextChange("")
                        },
                        onAddClick = {
                            onIsShowUploadDialogChange(true)
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
                        isPersonal = isPersonal,
                        onClickInviteMember = onClickInviteMember,
                        onClickMember = {
                            if (it.id == userId) {
                                return@ChatSideBarScreen
                            }
                            onsOtherProfileStateChange(it)

                            onShowOtherProfileBottomSheetChange(true)
                        },
                        onClickLeft = {
                            if (!isPersonal) {
                                viewModel.leftRoom(chatRoomId)
                            }
                        },
                        onClickNotification = {
                            onNotificationStateChange(!notificationState)
                        },
                        onClickSetting = { },
                    )
                }
            },
            onSideBarClose = {
                coroutineScope.launch {
                    anchoredState.animateTo(DragState.END)
                    onIsOpenSidebarChange(false)
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
                            onResendChatItemChange(it)
                            onIsShowReSendDialog(true)
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
                    },
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
                                        onChatLongClick = {
                                            onChatLongClick(item.message)
                                        },
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
                                        onChatLongClick = {
                                            onChatLongClick(item.message)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.Date ->
                                    ChatItemType.Date(item.timestamp.toFullFormatString())

                                is MessageRoomEvent.MessageParent.Img -> {
                                    val readUser = item.getUserCount(state.roomInfo?.members ?: persistentListOf())
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    val userInfo = state.users[item.userId]
                                    ChatItemType.Image(
                                        onClick = {
                                            onSelectUrlImageItemChange(item)
                                            onShowSelectUrlImagePreview(true)
                                        },
                                        image = item.url,
                                        isMe = item.userId == userId,
                                        count = if (count <= 0) null else count,
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        createdAt = item.timestamp.toAmShortString(),
                                        userName = userInfo?.name?: "",
                                        userProfile = userInfo?.picture?.ifEmpty { null },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.File -> {
                                    val readUser = item.getUserCount(state.roomInfo?.members ?: persistentListOf())
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    val userInfo = state.users[item.userId]
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
                                        count = if (count <= 0) null else count,
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        createdAt = item.timestamp.toAmShortString(),
                                        userName = userInfo?.name?: "",
                                        userProfile = userInfo?.picture?.ifEmpty { null },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.Left -> {
                                    val userNames = item.eventList
                                        .map {
                                            state.users.get(it)?.name ?: ""
                                        }
                                        .reduce { acc, s -> "$acc, $s" }

                                    ChatItemType.Else("${userNames}님이 방에서 퇴장하셨습니다.")
                                }

                                is MessageRoomEvent.MessageParent.Enter -> {
                                    val userNames = item.eventList
                                        .map {
                                            state.users.get(it)?.name ?: ""
                                        }
                                        .reduce { acc, s -> "$acc, $s" }

                                    ChatItemType.Else("${userNames}님이 방에 입장하셨습니다.")
                                }

                                is MessageRoomEvent.MessageParent.Etc -> ChatItemType.Else(item.toString())
                                is MessageRoomEvent.MessageParent.BOT.Meal -> {
                                    val readUser = item.getUserCount(state.roomInfo?.members ?: persistentListOf())
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size

                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.visibleMessage,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.visibleMessage)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.Timetable -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.visibleMessage,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.visibleMessage)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.Notification -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.visibleMessage,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.visibleMessage)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.DrawLots -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.visibleMessage,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.visibleMessage)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.TeamBuild -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.visibleMessage,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.visibleMessage)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.Etc -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.message,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.message)
                                        },
                                    )
                                }

                                is MessageRoomEvent.MessageParent.BOT.NotSupport -> {
                                    val readUser = item.getUserCount(
                                        state.roomInfo?.members ?: persistentListOf(),
                                    )
                                    val count = (state.roomInfo?.members?.size ?: 0) - readUser.size
                                    ChatItemType.Ai(
                                        isFirst = item.isFirst,
                                        isLast = item.isLast,
                                        message = item.message,
                                        createdAt = item.timestamp.toAmShortString(),
                                        count = if (count <= 0) null else count,
                                        onChatLongClick = {
                                            onChatLongClick(item.message)
                                        },
                                    )
                                }
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
                    onIsShowUploadDialogChange(false)
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
                            onShowImagePreview(false)
                            viewModel.channelSend(
                                userId = userId,
                                image = selectedImageBitmap!!,
                                fileName = selectedFileName,
                            )
                            onSelectedFileName("")
                            onSelectedImageBitmap(null)
                        }
                    },
                    popBackStack = {
                        onShowImagePreview(false)
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
                        onSelectUrlImageItemChange(null)
                        onShowSelectUrlImagePreview(false)
                    },
                )
            }
        }
    }
}
