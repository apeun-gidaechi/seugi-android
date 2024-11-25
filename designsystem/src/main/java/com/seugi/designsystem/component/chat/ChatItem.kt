package com.seugi.designsystem.component.chat

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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.util.fastForEach
import coil.compose.AsyncImage
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.AlphaIndication
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.AvatarType
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.SeugiAvatar
import com.seugi.designsystem.component.SeugiEmoji
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

val CHAT_SHAPE = 8.dp

@Immutable
data class ChatItemEmoji(
    val id: Int,
    val emoji: String,
    val count: Int,
    val isChecked: Boolean,
)

sealed interface ChatItemType {
    data class Others(
        val isFirst: Boolean,
        val isLast: Boolean,
        val userName: String,
        val userProfile: String?,
        val message: String,
        val createdAt: String,
        val count: Int?,
        val emojis: ImmutableList<ChatItemEmoji>,
        val onChatLongClick: () -> Unit = {},
        val onDateClick: () -> Unit = {},
        val onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
    ) : ChatItemType
    data class Me(
        val isLast: Boolean,
        val message: String,
        val createdAt: String,
        val count: Int?,
        val emojis: ImmutableList<ChatItemEmoji>,
        val onChatLongClick: () -> Unit = {},
        val onDateClick: () -> Unit = {},
        val onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
    ) : ChatItemType
    data class Date(
        val createdAt: String,
    ) : ChatItemType
    data class Ai(
        val isFirst: Boolean,
        val isLast: Boolean,
        val message: String,
        val createdAt: String,
        val count: Int?,
        val emojis: ImmutableList<ChatItemEmoji>,
        val onChatLongClick: () -> Unit = {},
        val onDateClick: () -> Unit = {},
        val onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
    ) : ChatItemType
    data class Else(
        val message: String,
    ) : ChatItemType

    data class Failed(
        val message: String,
        val onClickRetry: () -> Unit,
    ) : ChatItemType

    data class Sending(
        val message: String,
    ) : ChatItemType

    data class File(
        val onClick: () -> Unit,
        val isMe: Boolean,
        val fileName: String,
        val fileSize: String,
        val createdAt: String,
        val count: Int?,
        val emojis: ImmutableList<ChatItemEmoji>,
        val isFirst: Boolean,
        val isLast: Boolean,
        val userName: String,
        val userProfile: String?,
        val onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
    ) : ChatItemType

    data class FileFailed(
        val onClickRetry: () -> Unit,
        val fileName: String,
        val fileSize: String,
    ) : ChatItemType

    data class FileSending(
        val fileName: String,
        val fileSize: String,
    ) : ChatItemType

    data class Image(
        val onClick: () -> Unit,
        val isMe: Boolean,
        val image: String,
        val createdAt: String,
        val count: Int?,
        val emojis: ImmutableList<ChatItemEmoji>,
        val isFirst: Boolean,
        val isLast: Boolean,
        val userName: String,
        val userProfile: String?,
        val onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
    ) : ChatItemType

    data class ImageFailedUrl(
        val onClickRetry: () -> Unit,
        val image: String,
    ) : ChatItemType

    data class ImageFailedBitmap(
        val onClickRetry: () -> Unit,
        val image: ImageBitmap,
    ) : ChatItemType

    data class ImageSending(
        val image: ImageBitmap,
    ) : ChatItemType

    data class ImageSendingUrl(
        val image: String,
    ) : ChatItemType
}

@Composable
fun SeugiChatItem(modifier: Modifier = Modifier, type: ChatItemType) {
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
                emojis = type.emojis,
                onChatLongClick = type.onChatLongClick,
                onDateClick = type.onDateClick,
                onEmojiClick = type.onEmojiClick,
            )
        }
        is ChatItemType.Me -> {
            SeugiChatItemMe(
                modifier = modifier,
                isLast = type.isLast,
                message = type.message,
                createdAt = type.createdAt,
                count = type.count,
                emojis = type.emojis,
                onChatLongClick = type.onChatLongClick,
                onDateClick = type.onDateClick,
                onEmojiClick = type.onEmojiClick,
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
                emojis = type.emojis,
                onChatLongClick = type.onChatLongClick,
                onDateClick = type.onDateClick,
                onEmojiClick = type.onEmojiClick,
            )
        }
        is ChatItemType.Else -> {
            SeugiChatItemElse(
                modifier = modifier,
                message = type.message,
            )
        }
        is ChatItemType.Failed -> {
            SeugiChatItemFailed(
                modifier = modifier,
                message = type.message,
                onClickRetry = type.onClickRetry,
            )
        }
        is ChatItemType.Sending -> {
            SeugiChatItemSending(
                modifier = modifier,
                message = type.message,
            )
        }
        is ChatItemType.File -> {
            SeugiChatItemFile(
                modifier = modifier,
                onClick = type.onClick,
                isMe = type.isMe,
                fileName = type.fileName,
                fileSize = type.fileSize,
                isFirst = type.isFirst,
                isLast = type.isLast,
                createdAt = type.createdAt,
                userName = type.userName,
                userProfile = type.userProfile,
                count = type.count,
                emojis = type.emojis,
                onEmojiClick = type.onEmojiClick,
            )
        }
        is ChatItemType.FileFailed -> {
            SeugiChatItemFileFailed(
                modifier = modifier,
                onClickRetry = type.onClickRetry,
                fileName = type.fileName,
                fileSize = type.fileSize,
            )
        }
        is ChatItemType.FileSending -> {
            SeugiChatItemFileSending(
                modifier = modifier,
                fileName = type.fileName,
                fileSize = type.fileSize,
            )
        }
        is ChatItemType.Image -> {
            SeugiChatItemImage(
                modifier = modifier,
                onClick = type.onClick,
                isMe = type.isMe,
                image = type.image,
                isFirst = type.isFirst,
                isLast = type.isLast,
                createdAt = type.createdAt,
                userName = type.userName,
                userProfile = type.userProfile,
                count = type.count,
                emojis = type.emojis,
                onEmojiClick = type.onEmojiClick,
            )
        }

        is ChatItemType.ImageFailedBitmap -> {
            SeugiChatItemImageFailed(
                modifier = modifier,
                onClickRetry = type.onClickRetry,
                image = type.image,
            )
        }
        is ChatItemType.ImageFailedUrl -> {
            SeugiChatItemImageFailed(
                onClickRetry = type.onClickRetry,
                image = type.image,
            )
        }
        is ChatItemType.ImageSending -> {
            SeugiChatItemImageSending(
                modifier = modifier,
                image = type.image,
            )
        }

        is ChatItemType.ImageSendingUrl -> {
            SeugiChatItemImageSending(
                modifier = modifier,
                image = type.image,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
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
    emojis: ImmutableList<ChatItemEmoji>,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
    onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
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
                    style = SeugiTheme.typography.body1,
                    color = SeugiTheme.colors.gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Column(
                    modifier = Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        )
                        .dropShadow(
                            type = DropShadowType.EvBlack1,
                        )
                        .background(
                            color = SeugiTheme.colors.white,
                            shape = chatShape,
                        )
                        .clip(chatShape)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = AlphaIndication,
                            onClick = {},
                            onLongClick = onChatLongClick,
                        ),
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 8.dp
                        ),
                        text = message,
                        color = SeugiTheme.colors.black,
                        style = SeugiTheme.typography.body1,
                    )
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        emojis.fastForEach { emoji ->
                            SeugiEmoji(
                                emoji = emoji.emoji,
                                count = emoji.count,
                                isChecked = emoji.isChecked,
                                onClick = {
                                    onEmojiClick(emoji)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(if (emojis.isEmpty()) 4.dp else 12.dp))
                }
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
                        color = SeugiTheme.colors.gray600,
                        style = SeugiTheme.typography.caption1,
                    )
                    if (isLast) {
                        Text(
                            text = createdAt,
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption2,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SeugiChatItemMe(
    modifier: Modifier = Modifier,
    isLast: Boolean,
    message: String,
    createdAt: String,
    count: Int?,
    emojis: ImmutableList<ChatItemEmoji>,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
    onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
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
                color = SeugiTheme.colors.gray600,
                style = SeugiTheme.typography.caption1,
            )
            if (isLast) {
                Text(
                    text = createdAt,
                    color = SeugiTheme.colors.gray600,
                    style = SeugiTheme.typography.caption2,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .dropShadow(
                    type = DropShadowType.EvBlack1,
                )
                .background(
                    color = SeugiTheme.colors.primary500,
                    shape = chatShape,
                )
                .clip(chatShape)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AlphaIndication,
                    onClick = {},
                    onLongClick = onChatLongClick,
                ),
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp,
                    bottom = 8.dp
                ),
                text = message,
                color = SeugiTheme.colors.white,
                style = SeugiTheme.typography.body1,
            )
            FlowRow(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                emojis.fastForEach { emoji ->
                    SeugiEmoji(
                        emoji = emoji.emoji,
                        count = emoji.count,
                        isChecked = emoji.isChecked,
                        onClick = {
                            onEmojiClick(emoji)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(if (emojis.isEmpty()) 4.dp else 12.dp))
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
                    color = SeugiTheme.colors.gray100,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = createdAt,
            color = SeugiTheme.colors.gray600,
            style = SeugiTheme.typography.caption2,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SeugiChatItemAi(
    modifier: Modifier = Modifier,
    isFirst: Boolean,
    isLast: Boolean,
    message: String,
    createdAt: String,
    count: Int?,
    emojis: ImmutableList<ChatItemEmoji>,
    onChatLongClick: () -> Unit,
    onDateClick: () -> Unit,
    onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
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
                    style = SeugiTheme.typography.body1,
                    color = SeugiTheme.colors.gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Column(
                    modifier = Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        )
                        .dropShadow(
                            type = DropShadowType.EvBlack1,
                        )
                        .background(
                            color = SeugiTheme.colors.white,
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
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 8.dp
                        ),
                        text = message,
                        color = SeugiTheme.colors.black,
                        style = SeugiTheme.typography.body1,
                    )

                    FlowRow(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        emojis.fastForEach { emoji ->
                            SeugiEmoji(
                                emoji = emoji.emoji,
                                count = emoji.count,
                                isChecked = emoji.isChecked,
                                onClick = {
                                    onEmojiClick(emoji)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(if (emojis.isEmpty()) 4.dp else 12.dp))
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
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption1,
                        )
                        Text(
                            text = createdAt,
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption2,
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
                    color = SeugiTheme.colors.gray100,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = message,
            color = SeugiTheme.colors.gray600,
            style = SeugiTheme.typography.caption2,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemFailed(modifier: Modifier = Modifier, message: String, onClickRetry: () -> Unit) {
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
        ChatItemRetryIcon(
            onClickRetry = onClickRetry,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .dropShadow(
                    type = DropShadowType.EvBlack1,
                )
                .background(
                    color = SeugiTheme.colors.primary500,
                    shape = chatShape,
                )
                .clip(chatShape)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AlphaIndication,
                    onClick = {},
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = message,
                color = SeugiTheme.colors.white,
                style = SeugiTheme.typography.body1,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemSending(modifier: Modifier = Modifier, message: String) {
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
        ChatItemSendIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .dropShadow(
                    type = DropShadowType.EvBlack1,
                )
                .background(
                    color = SeugiTheme.colors.primary500,
                    shape = chatShape,
                )
                .clip(chatShape)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AlphaIndication,
                    onClick = {},
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = message,
                color = SeugiTheme.colors.white,
                style = SeugiTheme.typography.body1,
            )
        }
    }
}

@Composable
private fun SeugiChatItemFile(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isMe: Boolean,
    fileName: String,
    fileSize: String,
    createdAt: String,
    count: Int?,
    emojis: ImmutableList<ChatItemEmoji>,
    isLast: Boolean,
    isFirst: Boolean,
    userName: String,
    userProfile: String?,
    onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
) {
    val chatShape = RoundedCornerShape(CHAT_SHAPE)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp * 0.7f
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
    ) {
        if (!isMe) {
            if (isFirst) {
                SeugiAvatar(
                    type = AvatarType.Medium,
                    image = userProfile,
                )
            } else {
                Spacer(modifier = Modifier.width(32.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            if (!isMe && isFirst) {
                Text(
                    text = userName,
                    style = SeugiTheme.typography.body1,
                    color = SeugiTheme.colors.gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                if (isMe) {
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = count?.toString() ?: "",
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption1,
                        )
                        if (isLast) {
                            Text(
                                text = createdAt,
                                color = SeugiTheme.colors.gray600,
                                style = SeugiTheme.typography.caption2,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Row(
                    modifier = modifier
                        .widthIn(
                            min = min(screenWidth, 128.dp),
                            max = min(screenWidth, 320.dp),
                        )
                        .dropShadow(type = DropShadowType.EvBlack1)
                        .background(
                            color = SeugiTheme.colors.white,
                            shape = chatShape,
                        )
                        .bounceClick(onClick),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                top = 14.dp,
                                bottom = 14.dp,
                            )
                            .background(
                                color = SeugiTheme.colors.primary500,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.ic_file_line),
                            contentDescription = "파일 아이콘",
                            colorFilter = ColorFilter.tint(SeugiTheme.colors.white),
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = fileName,
                            color = SeugiTheme.colors.black,
                            style = SeugiTheme.typography.body1,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = fileSize,
                            color = SeugiTheme.colors.gray500,
                            style = SeugiTheme.typography.caption2,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_stop_down_line),
                        contentDescription = "다운로드 아이콘",
                        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                    )
                }
                if (!isMe) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = count?.toString() ?: "",
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption1,
                        )
                        if (isLast) {
                            Text(
                                text = createdAt,
                                color = SeugiTheme.colors.gray600,
                                style = SeugiTheme.typography.caption2,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeugiChatItemFileFailed(modifier: Modifier = Modifier, onClickRetry: () -> Unit, fileName: String, fileSize: String) {
    val chatShape = RoundedCornerShape(CHAT_SHAPE)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp * 0.8f
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemRetryIcon(onClickRetry = onClickRetry)
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .widthIn(
                    min = min(screenWidth, 128.dp),
                    max = min(screenWidth, 320.dp),
                )
                .dropShadow(type = DropShadowType.EvBlack1)
                .background(
                    color = SeugiTheme.colors.white,
                    shape = chatShape,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 14.dp,
                        bottom = 14.dp,
                    )
                    .background(
                        color = SeugiTheme.colors.primary500,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_file_line),
                    contentDescription = "파일 아이콘",
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.white),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = fileName,
                    color = SeugiTheme.colors.black,
                    style = SeugiTheme.typography.body1,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = fileSize,
                    color = SeugiTheme.colors.gray500,
                    style = SeugiTheme.typography.caption2,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_expand_stop_down_line),
                contentDescription = "다운로드 아이콘",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
            )
        }
    }
}

@Composable
private fun SeugiChatItemFileSending(modifier: Modifier = Modifier, fileName: String, fileSize: String) {
    val chatShape = RoundedCornerShape(CHAT_SHAPE)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp * 0.8f
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemSendIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .widthIn(
                    min = min(screenWidth, 128.dp),
                    max = min(screenWidth, 320.dp),
                )
                .dropShadow(type = DropShadowType.EvBlack1)
                .background(
                    color = SeugiTheme.colors.white,
                    shape = chatShape,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 14.dp,
                        bottom = 14.dp,
                    )
                    .background(
                        color = SeugiTheme.colors.primary500,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_file_line),
                    contentDescription = "파일 아이콘",
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.white),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = fileName,
                    color = SeugiTheme.colors.black,
                    style = SeugiTheme.typography.body1,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = fileSize,
                    color = SeugiTheme.colors.gray500,
                    style = SeugiTheme.typography.caption2,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_expand_stop_down_line),
                contentDescription = "다운로드 아이콘",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
            )
        }
    }
}

@Composable
fun SeugiChatItemImage(
    modifier: Modifier = Modifier,
    isMe: Boolean,
    onClick: () -> Unit,
    image: String,
    isFirst: Boolean,
    isLast: Boolean,
    count: Int?,
    emojis: ImmutableList<ChatItemEmoji>,
    createdAt: String,
    userName: String,
    userProfile: String?,
    onEmojiClick: (emoji: ChatItemEmoji) -> Unit,
) {
    val configuration = LocalConfiguration.current

    val topBarHeight = 54.dp
    val bottomTextFieldHeight = 64.dp

    val screenWidth = configuration.screenWidthDp.dp * 0.7f
    val screenHeight = (configuration.screenHeightDp.dp - topBarHeight - bottomTextFieldHeight) * 0.8f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
    ) {
        if (!isMe) {
            if (isFirst) {
                SeugiAvatar(
                    type = AvatarType.Medium,
                    image = userProfile,
                )
            } else {
                Spacer(modifier = Modifier.width(32.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            if (isMe) {
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = count?.toString() ?: "",
                        color = SeugiTheme.colors.gray600,
                        style = SeugiTheme.typography.caption1,
                    )
                    if (isLast) {
                        Text(
                            text = createdAt,
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption2,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column {
                if (!isMe && isFirst) {
                    Text(
                        text = userName,
                        style = SeugiTheme.typography.body1,
                        color = SeugiTheme.colors.gray600,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Row(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .bounceClick(onClick),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .widthIn(
                                min = min(screenWidth, 128.dp),
                                max = min(screenWidth, 320.dp),
                            )
                            .heightIn(
                                min = min(40.dp, screenHeight),
                                max = min(300.dp, screenHeight),
                            )
                            .wrapContentSize(),
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }
            }
            if (!isMe) {
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = count?.toString() ?: "",
                        color = SeugiTheme.colors.gray600,
                        style = SeugiTheme.typography.caption1,
                    )
                    if (isLast) {
                        Text(
                            text = createdAt,
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption2,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeugiChatItemImageFailed(modifier: Modifier = Modifier, onClickRetry: () -> Unit, image: ImageBitmap) {
    val configuration = LocalConfiguration.current

    val topBarHeight = 54.dp
    val bottomTextFieldHeight = 64.dp

    val screenWidth = configuration.screenWidthDp.dp * 0.8f
    val screenHeight = (configuration.screenHeightDp.dp - topBarHeight - bottomTextFieldHeight) * 0.8f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemRetryIcon(
            onClickRetry = onClickRetry,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(
                    horizontal = 8.dp,
                )
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier
                    .widthIn(
                        min = min(screenWidth, 128.dp),
                        max = min(screenWidth, 320.dp),
                    )
                    .heightIn(
                        min = min(40.dp, screenHeight),
                        max = min(300.dp, screenHeight),
                    )
                    .wrapContentSize(),
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
        }
    }
}

@Composable
fun SeugiChatItemImageFailed(modifier: Modifier = Modifier, onClickRetry: () -> Unit, image: String) {
    val configuration = LocalConfiguration.current

    val topBarHeight = 54.dp
    val bottomTextFieldHeight = 64.dp

    val screenWidth = configuration.screenWidthDp.dp * 0.8f
    val screenHeight = (configuration.screenHeightDp.dp - topBarHeight - bottomTextFieldHeight) * 0.8f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemRetryIcon(
            onClickRetry = onClickRetry,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(
                    horizontal = 8.dp,
                )
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .widthIn(
                        min = min(screenWidth, 128.dp),
                        max = min(screenWidth, 320.dp),
                    )
                    .heightIn(
                        min = min(40.dp, screenHeight),
                        max = min(300.dp, screenHeight),
                    )
                    .wrapContentSize(),
                model = image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
        }
    }
}

@Composable
fun SeugiChatItemImageSending(modifier: Modifier = Modifier, image: ImageBitmap) {
    val configuration = LocalConfiguration.current

    val topBarHeight = 54.dp
    val bottomTextFieldHeight = 64.dp

    val screenWidth = configuration.screenWidthDp.dp * 0.8f
    val screenHeight = (configuration.screenHeightDp.dp - topBarHeight - bottomTextFieldHeight) * 0.8f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemSendIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(
                    horizontal = 8.dp,
                )
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier
                    .widthIn(
                        min = min(screenWidth, 128.dp),
                        max = min(screenWidth, 320.dp),
                    )
                    .heightIn(
                        min = min(40.dp, screenHeight),
                        max = min(300.dp, screenHeight),
                    )
                    .wrapContentSize(),
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
        }
    }
}

@Composable
fun SeugiChatItemImageSending(modifier: Modifier = Modifier, image: String) {
    val configuration = LocalConfiguration.current

    val topBarHeight = 54.dp
    val bottomTextFieldHeight = 64.dp

    val screenWidth = configuration.screenWidthDp.dp * 0.8f
    val screenHeight = (configuration.screenHeightDp.dp - topBarHeight - bottomTextFieldHeight) * 0.8f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        ChatItemSendIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = modifier
                .padding(
                    horizontal = 8.dp,
                )
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .widthIn(
                        min = min(screenWidth, 128.dp),
                        max = min(screenWidth, 320.dp),
                    )
                    .heightIn(
                        min = min(40.dp, screenHeight),
                        max = min(300.dp, screenHeight),
                    )
                    .wrapContentSize(),
                model = image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
        }
    }
}

@Composable
private fun ChatItemSendIcon(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(20.dp)
            .rotate(-90f),
        painter = painterResource(id = R.drawable.ic_send_fill),
        contentDescription = "보내는 중",
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray400),
    )
}

@Composable
private fun ChatItemRetryIcon(modifier: Modifier = Modifier, onClickRetry: () -> Unit) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = SeugiTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp),
            )
            .bounceClick(onClickRetry),
    ) {
        Image(
            modifier = Modifier
                .padding(6.dp)
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_refresh_fill),
            contentDescription = "다시하기",
            colorFilter = ColorFilter.tint(SeugiTheme.colors.gray800),
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(28.dp)
                .background(SeugiTheme.colors.gray200),
        )
        Image(
            modifier = Modifier
                .padding(6.dp)
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_close_line),
            contentDescription = "다시하기",
            colorFilter = ColorFilter.tint(SeugiTheme.colors.red500),
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
                    onEmojiClick = {}
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
                    onEmojiClick = {}
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
                    onEmojiClick = {}
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
                    onEmojiClick = {}
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Failed(
                    message = "iOS 정말 재미없어요",
                    onClickRetry = {},
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeugiChatItem(
                modifier = Modifier.align(Alignment.End),
                type = ChatItemType.Sending(
                    message = "iOS 정말 재미없어요",
                ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            SeugiChatItem(
                type = ChatItemType.Date(
                    createdAt = "2024년 3월 21일 목요일",
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiChatItem(
                type = ChatItemType.Else(
                    message = "챗스기님이 입장하셨습니다.",
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiChatItem(
                type = ChatItemType.File(
                    onClick = {},
                    isMe = true,
                    fileName = "B1nd인턴+여행계획서.pptx",
                    fileSize = "191.3KB",
                    count = 1,
                    createdAt = "오후 12:44",
                    isFirst = true,
                    isLast = true,
                    userName = "test",
                    userProfile = null,
                    emojis = persistentListOf(),
                    onEmojiClick = {}
                ),
            )
        }
    }
}
