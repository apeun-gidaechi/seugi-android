package com.apeun.gidaechi.designsystem.component.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.AlphaIndication
import com.apeun.gidaechi.designsystem.component.AvatarType
import com.apeun.gidaechi.designsystem.component.GradientPrimary
import com.apeun.gidaechi.designsystem.component.SeugiAvatar
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

val CHAT_SHAPE = 8.dp

sealed class ChatItemType {
    data class Others(
        val isFirst: Boolean,
        val isLast: Boolean,
        val userName: String,
        val userProfile: String?,
        val message: String,
        val createdAt: String,
        val count: Int?,
    ) : ChatItemType()
    data class Me(
        val isLast: Boolean,
        val message: String,
        val createdAt: String,
        val count: Int?,
    ) : ChatItemType()
    data class Date(
        val createdAt: String,
    ) : ChatItemType()
    data class Ai(
        val isFirst: Boolean,
        val isLast: Boolean,
        val message: String,
        val createdAt: String,
        val count: Int?,
    ) : ChatItemType()
    data class Else(
        val message: String,
    ) : ChatItemType()
}

@Composable
fun SeugiChatItem(modifier: Modifier = Modifier, type: ChatItemType, onChatLongClick: () -> Unit = {}, onDateClick: () -> Unit = {}) {
    when (type) {
        is ChatItemType.Others -> {
            SeugiChatItemOthers(
                modifier = modifier,
                isFirst = type.isFirst,
                isLast = type.isLast,
                userName = type.userName,
                userProfile = type.userProfile,
                message = type.message,
                createdAt = type.createdAt,
                count = type.count,
                onChatLongClick = onChatLongClick,
                onDateClick = onDateClick,
            )
        }
        is ChatItemType.Me -> {
            SeugiChatItemMe(
                modifier = modifier,
                isLast = type.isLast,
                message = type.message,
                createdAt = type.createdAt,
                count = type.count,
                onChatLongClick = onChatLongClick,
                onDateClick = onDateClick,
            )
        }
        is ChatItemType.Date -> {
            SeugiChatItemDate(
                modifier = modifier,
                createdAt = type.createdAt,
            )
        }
        is ChatItemType.Ai -> {
            SeugiChatItemAi(
                modifier = modifier,
                isFirst = type.isFirst,
                isLast = type.isLast,
                message = type.message,
                createdAt = type.createdAt,
                count = type.count,
                onChatLongClick = onChatLongClick,
                onDateClick = onDateClick,
            )
        }
        is ChatItemType.Else -> {
            SeugiChatItemElse(
                modifier = modifier,
                message = type.message,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemOthers(
    modifier: Modifier = Modifier,
    isFirst: Boolean,
    isLast: Boolean,
    userName: String,
    userProfile: String?,
    message: String,
    createdAt: String,
    count: Int?,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    val chatShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = CHAT_SHAPE,
        bottomStart = CHAT_SHAPE,
        bottomEnd = CHAT_SHAPE,
    )
    Row(
        modifier = modifier,
    ) {
        if (isFirst) {
            SeugiAvatar(
                type = AvatarType.Medium,
                image = userProfile,
            )
        } else {
            Spacer(modifier = Modifier.width(32.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            if (isFirst) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    modifier = Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        )
                        .dropShadow(
                            type = DropShadowType.EvBlack1,
                        )
                        .background(
                            color = White,
                            shape = chatShape,
                        )
                        .clip(chatShape)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = AlphaIndication,
                            onClick = {},
                            onLongClick = onChatLongClick,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = message,
                        color = Black,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (isLast) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .clickable(
                                onClick = onDateClick,
                            ),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = count?.toString() ?: "",
                            color = Gray600,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            text = createdAt,
                            color = Gray600,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemMe(
    modifier: Modifier = Modifier,
    isLast: Boolean,
    message: String,
    createdAt: String,
    count: Int?,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    val chatShape = RoundedCornerShape(
        topStart = CHAT_SHAPE,
        topEnd = 0.dp,
        bottomStart = CHAT_SHAPE,
        bottomEnd = CHAT_SHAPE,
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        if (isLast) {
            Column(
                modifier = Modifier
                    .clickable(
                        onClick = onDateClick,
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = count?.toString() ?: "",
                    color = Gray600,
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = createdAt,
                    color = Gray600,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Box(
            modifier = Modifier
                .dropShadow(
                    type = DropShadowType.EvBlack1,
                )
                .background(
                    color = Primary500,
                    shape = chatShape,
                )
                .clip(chatShape)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AlphaIndication,
                    onClick = {},
                    onLongClick = onChatLongClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = message,
                color = White,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun SeugiChatItemDate(modifier: Modifier, createdAt: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = Gray100,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = createdAt,
            color = Gray600,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemAi(
    modifier: Modifier = Modifier,
    isFirst: Boolean,
    isLast: Boolean,
    message: String,
    createdAt: String,
    count: Int?,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    val chatShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = CHAT_SHAPE,
        bottomStart = CHAT_SHAPE,
        bottomEnd = CHAT_SHAPE,
    )
    Row(
        modifier = modifier,
    ) {
        if (isFirst) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_appicon_round),
                contentDescription = "앱 둥근 로고",
            )
        } else {
            Spacer(modifier = Modifier.width(32.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            if (isFirst) {
                Text(
                    text = "캣스기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    modifier = Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        )
                        .dropShadow(
                            type = DropShadowType.EvBlack1,
                        )
                        .background(
                            color = White,
                            shape = chatShape,
                        )
                        .border(
                            width = (1.5).dp,
                            brush = GradientPrimary,
                            shape = chatShape,
                        )
                        .clip(chatShape)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = AlphaIndication,
                            onClick = {},
                            onLongClick = onChatLongClick,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = message,
                        color = Black,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (isLast) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .clickable(
                                onClick = onDateClick,
                            ),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = count?.toString() ?: "",
                            color = Gray600,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            text = createdAt,
                            color = Gray600,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SeugiChatItemElse(modifier: Modifier, message: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = Gray100,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = message,
            color = Gray600,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview
@Composable
private fun PreviewSeugiChatItem() {
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
                },
                onDateClick = {
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
                },
                onDateClick = {
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
                },
                onDateClick = {
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
                },
                onDateClick = {
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                type = ChatItemType.Date(
                    createdAt = "2024년 3월 21일 목요일",
                ),
            )
            SeugiChatItem(
                type = ChatItemType.Else(
                    message = "챗스기님이 입장하셨습니다.",
                ),
            )
        }
    }
}
