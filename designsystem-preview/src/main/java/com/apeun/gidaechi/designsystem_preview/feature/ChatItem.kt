package com.apeun.gidaechi.designsystem_preview.feature

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.chat.ChatItemType
import com.apeun.gidaechi.designsystem.component.chat.SeugiChatItem
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.SeugiTheme


@Composable
fun ChatItem() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary050),
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
                ),
                onChatLongClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
                onDateClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
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
                ),
                onChatLongClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
                onDateClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Me(
                    isLast = false,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                ),
                onChatLongClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
                onDateClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Me(
                    isLast = true,
                    message = "iOS정말 재미없어요!",
                    createdAt = "오후 7:44",
                    count = 1,
                ),
                onChatLongClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
                onDateClick = {
                    Log.d("TAG", "PreviewSeugiChatItem: ")
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                type = ChatItemType.Date(
                    createdAt = "2024년 3월 21일 목요일",
                ),
            )
        }
    }
}
