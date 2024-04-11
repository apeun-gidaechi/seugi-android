package com.apeun.gidaechi.chatdatail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.common.utiles.toAmShortString
import com.apeun.gidaechi.common.utiles.toFullFormatString
import com.apeun.gidaechi.common.utiles.toShortString
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.SeugiIconButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.chat.ChatItemType
import com.apeun.gidaechi.designsystem.component.chat.SeugiChatItem
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.component.textfield.SeugiChatTextField
import com.apeun.gidaechi.designsystem.component.textfield.SeugiTextField
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Primary050
import java.time.Duration
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    onNavigationVisibleChange: (Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var text by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        onNavigationVisibleChange(false)
        viewModel.loadMessage()
    }

    LifecycleResumeEffect(key1 = Unit) {
        onPauseOrDispose {
            onNavigationVisibleChange(true)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary050),
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = state.roomInfo?.roomName?: "",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    SeugiIconButton(
                        resId = R.drawable.ic_search,
                        size = 28.dp,
                        onClick = {

                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SeugiIconButton(
                        resId = R.drawable.ic_hamburger_horizontal_line,
                        size = 28.dp,
                        onClick = {

                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                },
                backIconCheck = true,
                shadow = true,
                onNavigationIconClick = {
                    popBackStack()
                }
            )
        },
        bottomBar = {
            SeugiChatTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    )
                    .dropShadow(DropShadowType.Ev1),
                value = text,
                placeholder = "메세지 보내기",
                onValueChange = {
                    text = it
                },
                onSendClick = {
                    text = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Primary050),
            contentPadding = PaddingValues(
                horizontal = 8.dp
            )
        ) {
            items(state.message.size) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp
                        )
                ) {
                    val formerItem = state.message.getOrNull(index-1)
                    val nextItem = state.message.getOrNull(index+1)
                    val item = state.message[index]

                    val isMe = item.userId == state.userInfo?.id
                    val isLast = item.userId != nextItem?.userId ||
                            (Duration.between(item.createdAt, nextItem.createdAt).seconds >= 86400 && item.userId == nextItem.userId)


                    var isFirst = item.userId != formerItem?.userId
                    if (formerItem != null && Duration.between(formerItem.createdAt, item.createdAt).seconds >= 86400) {
                        isFirst = true
                        Spacer(modifier = Modifier.height(12.dp))
                        SeugiChatItem(type = ChatItemType.Date(item.createdAt.toFullFormatString()))
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    val alignModifier = if (isMe) Modifier.align(Alignment.End) else Modifier

                    SeugiChatItem(
                        modifier = Modifier
                            .then(alignModifier),
                        type = when {
                            isMe -> ChatItemType.Me(
                                isLast = isLast,
                                message = item.message,
                                createdAt = item.createdAt.toAmShortString(),
                                count = 1
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
                        }
                    )
                }
            }
        }
    }
}