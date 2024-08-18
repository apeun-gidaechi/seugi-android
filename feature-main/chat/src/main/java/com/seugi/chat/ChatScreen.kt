package com.seugi.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.common.utiles.toAmShortString
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.chat.SeugiChatList
import com.seugi.designsystem.theme.Gray400
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.Primary500
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
internal fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), workspaceId: String, navigateToChatDetail: (chatID: String) -> Unit) {
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

    BackHandler(
        enabled = isSearchMode,
        onBack = onDone,
    )

    LaunchedEffect(key1 = true) {
        viewModel.loadChats(
            workspaceId = workspaceId,
        )
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    if (isSearchMode) {
                        ChatTextField(
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
                            text = "채팅",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },
                actions = {
                    if (!isSearchMode) {
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
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it),
        ) {
            items(state.chatItems) { item ->
                SeugiChatList(
                    userName = item.chatName,
                    message = item.lastMessage ?: "",
                    createdAt = item.lastMessageTimestamp?.toAmShortString() ?: "",
                    count = item.notReadCnt,
                    onClick = {
                        navigateToChatDetail(item.id)
                    },
                )
            }
        }
    }
}

@Composable
private fun ChatTextField(searchText: String, onValueChange: (String) -> Unit, placeholder: String = "", enabled: Boolean = true, onDone: () -> Unit) {
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
