package com.seugi.designsystem.component.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.theme.SeugiTheme

sealed interface ChatRoomType {
    data object ExtraSmall : ChatRoomType
    data object Small : ChatRoomType
    data object Medium : ChatRoomType
    data object Large : ChatRoomType
    data object ExtraLarge : ChatRoomType
    data object XXL : ChatRoomType
}

private fun ChatRoomType.size() =
    when (this) {
        ChatRoomType.ExtraLarge -> 64.dp
        ChatRoomType.ExtraSmall -> 16.dp
        ChatRoomType.Large -> 36.dp
        ChatRoomType.Medium -> 32.dp
        ChatRoomType.Small -> 24.dp
        ChatRoomType.XXL -> 128.dp
    }

@Composable
private fun ChatRoomType.typography() =
    when(this) {
        ChatRoomType.ExtraLarge -> SeugiTheme.typography.title1
        ChatRoomType.ExtraSmall -> SeugiTheme.typography.caption2
        ChatRoomType.Large -> SeugiTheme.typography.subtitle1
        ChatRoomType.Medium -> SeugiTheme.typography.body1
        ChatRoomType.Small -> SeugiTheme.typography.caption1
        ChatRoomType.XXL -> SeugiTheme.typography.display1
    }

@Composable
fun SeugiChatRoom(modifier: Modifier = Modifier, text: String = "", type: ChatRoomType) {
    val typography = type.typography()

    Box(
        modifier = modifier
            .size(type.size())
            .clip(CircleShape)
            .background(
                color = SeugiTheme.colors.primary200,
                shape = CircleShape,
            ),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (text.isEmpty()) "" else text.substring(startIndex = 0, endIndex = 1),
            color = SeugiTheme.colors.primary700,
            style = typography,
        )
    }
}
