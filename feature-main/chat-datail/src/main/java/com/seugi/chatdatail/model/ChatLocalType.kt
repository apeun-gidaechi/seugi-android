package com.seugi.chatdatail.model

import androidx.compose.ui.input.key.Key.Companion.I
import com.seugi.designsystem.component.chat.ChatItemType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed interface ChatLocalType {
    data class Failed(val text: String): ChatLocalType
    data class Send(val text: String): ChatLocalType
}

internal fun Collection<ChatLocalType>.findBySendingMessage(message: String): ChatLocalType? {
    for (i in this) {
        if (i is ChatLocalType.Send && i.text == message) {
            return i
        }
    }
    return null
}

internal operator fun ImmutableList<ChatLocalType>.plus(element: ChatLocalType): ImmutableList<ChatLocalType> {
    return this.toMutableList().apply {
        add(element)
    }.toImmutableList()
}

internal operator fun ImmutableList<ChatLocalType>.minus(type: ChatLocalType): ImmutableList<ChatLocalType> {
    return this.toMutableList().apply {
        remove(type)
    }.toImmutableList()
}