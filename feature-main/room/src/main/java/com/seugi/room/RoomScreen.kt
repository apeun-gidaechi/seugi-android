package com.seugi.room

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.common.utiles.toAmShortString
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.chat.SeugiChatList
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun RoomScreen(
    viewModel: RoomViewModel = hiltViewModel(),
    workspaceId: String,
    userId: Long,
    navigateToChatDetail: (roomId: String, workspaceId: String) -> Unit,
    navigateToCreateRoom: (workspaceId: String, userId: Long) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var isSearchMode by remember { mutableStateOf(false) }

    val onDone: () -> Unit = {
        coroutineScope.launch {
            searchText = ""
            isSearchMode = false
            viewModel.searchRoom("")
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = {
            viewModel.refresh(workspaceId)
        },
    )

    BackHandler(
        enabled = isSearchMode,
        onBack = onDone,
    )

    LaunchedEffect(key1 = workspaceId) {
        viewModel.loadChats(
            workspaceId = workspaceId,
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.white),
        topBar = {
            SeugiTopBar(
                title = {
                    if (isSearchMode) {
                        RoomTextField(
                            searchText = searchText,
                            onValueChange = {
                                searchText = it
                                viewModel.searchRoom(it)
                            },
                            enabled = true,
                            placeholder = "채팅방 검색",
                            onDone = onDone,
                        )
                    } else {
                        Text(
                            text = "단체",
                            style = SeugiTheme.typography.subtitle1,
                        )
                    }
                },
                actions = {
                    if (!isSearchMode) {
                        SeugiIconButton(
                            resId = R.drawable.ic_add_fill,
                            size = 28.dp,
                            onClick = {
                                navigateToCreateRoom(workspaceId, userId)
                            },
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        SeugiIconButton(
                            resId = R.drawable.ic_search,
                            size = 28.dp,
                            onClick = {
                                isSearchMode = true
                            },
                        )
                    }
                },
                shadow = true,
                onNavigationIconClick = if (isSearchMode) onDone else null,
            )
        },
        containerColor = SeugiTheme.colors.white,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.white)
                .padding(it)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter,
        ) {
            PullRefreshIndicator(
                modifier = Modifier.zIndex(1f),
                refreshing = state.isRefresh,
                state = pullRefreshState,
                contentColor = SeugiTheme.colors.primary500,
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(state.chatItems) { item ->
                    SeugiChatList(
                        userName = item.chatName,
                        userProfile = item.chatRoomImg?.ifEmpty { null },
                        message = item.lastMessage ?: "",
                        createdAt = item.lastMessageTimestamp?.toAmShortString() ?: "",
                        count = item.notReadCnt,
                        memberCount = item.memberList.toImmutableList().size,
                        onClick = {
                            navigateToChatDetail(item.id, item.workspaceId)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun RoomTextField(searchText: String, onValueChange: (String) -> Unit, placeholder: String = "", enabled: Boolean = true, onDone: () -> Unit) {
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
