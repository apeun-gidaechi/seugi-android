package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seugi.designsystem.component.chat.SeugiChatList
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun ChatList() {
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiChatList(
                userName = "노영재",
                message = "나 사실..",
                createdAt = "12:39",
                onClick = {
                },
            )
            SeugiChatList(
                userName = "노영재",
                message = "나 사실..",
                createdAt = "12:39",
                count = 72,
                onClick = {},
            )
            SeugiChatList(
                userName = "노영재너럴캡숑짱ㅋ",
                message = "정말 좋습니다",
                createdAt = "12:39",
                memberCount = 4,
                onClick = {},
            )
            SeugiChatList(
                userName = "노영재너럴캡숑짱ㅋ",
                message = "정말 좋습니다",
                createdAt = "12:39",
                count = 72,
                memberCount = 4,
                onClick = {},
            )
        }
    }
}
