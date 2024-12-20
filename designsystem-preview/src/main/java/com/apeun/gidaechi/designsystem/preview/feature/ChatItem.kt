package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.chat.ChatItemType
import com.seugi.designsystem.component.chat.SeugiChatItem
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ChatItem() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.primary050),
        ) {
            SeugiChatItem(
                type = ChatItemType.Others(
                    isFirst = true,
                    isLast = false,
                    userName = "이강현",
                    userProfile = null,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                type = ChatItemType.Others(
                    isFirst = false,
                    isLast = true,
                    userName = "이강현",
                    userProfile = null,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Me(
                    isLast = false,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Me(
                    isLast = true,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                type = ChatItemType.Date(
                    createdAt = "2024년 3월 21일 목요일",
                ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                type = ChatItemType.Ai(
                    isFirst = true,
                    isLast = false,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                type = ChatItemType.Ai(
                    isFirst = false,
                    isLast = true,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                    onDateClick = {},
                    onChatLongClick = {},
                    emojis = persistentListOf(),
                    onEmojiClick = {},
                ),
            )
        }
    }
}
