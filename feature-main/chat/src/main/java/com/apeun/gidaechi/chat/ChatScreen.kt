package com.apeun.gidaechi.chat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.common.utiles.toAmShortString
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.SeugiIconButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.chat.SeugiChatList

@ExperimentalMaterial3Api
@Composable
internal fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), navigateToChatDetail: (chatID: String, workspaceId: String) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadChats()
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "채팅",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                actions = {
                    SeugiIconButton(
                        resId = R.drawable.ic_search,
                        size = 28.dp,
                        onClick = { /*TODO*/ },
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                },
                shadow = true,
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
                    message = item.lastMessage,
                    createdAt = item.lastMessageTimestamp.toAmShortString(),
                    count = item.notReadCnt,
                    onClick = {
                        navigateToChatDetail(item.id, item.workspaceId)
                    },
                )
            }
        }
    }
}
