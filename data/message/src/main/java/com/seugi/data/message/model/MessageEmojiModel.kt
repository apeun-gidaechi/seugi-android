package com.seugi.data.message.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class MessageEmojiModel(
    val emojiId: Int,
    val userId: ImmutableList<Long>,
)

fun List<MessageEmojiModel>.addEmoji(userId: Long, emojiId: Int): List<MessageEmojiModel> {
    val emojiItems = this.toMutableList()
    emojiItems.forEachIndexed { index, messageEmojiModel ->
        if (messageEmojiModel.emojiId != emojiId) return@forEachIndexed
        emojiItems[index] = messageEmojiModel.copy(
            userId = messageEmojiModel.userId.toMutableList().apply {
                add(userId)
            }.toSet().toImmutableList(),
        )
        return emojiItems
    }
    emojiItems.add(
        MessageEmojiModel(
            emojiId = emojiId,
            userId = persistentListOf(userId),
        ),
    )
    return emojiItems
}

fun List<MessageEmojiModel>.minusEmoji(userId: Long, emojiId: Int): List<MessageEmojiModel> {
    val emojiItems = this.toMutableList()

    emojiItems.forEachIndexed { index, messageEmojiModel ->
        if (messageEmojiModel.emojiId != emojiId) return@forEachIndexed

        val userIds = messageEmojiModel.userId.minus(userId)
        if (userIds.isEmpty()) {
            emojiItems.removeAt(index)
            return emojiItems
        }
        emojiItems[index] = messageEmojiModel.copy(userId = userIds.toImmutableList())
        return emojiItems
    }
    return emojiItems
}
