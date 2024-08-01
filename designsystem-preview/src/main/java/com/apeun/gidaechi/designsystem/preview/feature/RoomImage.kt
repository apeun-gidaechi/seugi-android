package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seugi.designsystem.component.chat.ChatRoomType
import com.seugi.designsystem.component.chat.SeugiChatRoom

@Composable
fun RoomImage() {
    val text = "엄준식"
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.ExtraSmall,
        )
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.Small,
        )
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.Medium,
        )
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.Large,
        )
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.ExtraLarge,
        )
        SeugiChatRoom(
            text = text,
            type = ChatRoomType.XXL,
        )
    }
}
