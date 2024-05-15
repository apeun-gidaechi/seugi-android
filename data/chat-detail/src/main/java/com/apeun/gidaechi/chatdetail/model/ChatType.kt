package com.apeun.gidaechi.chatdetail.model

enum class ChatType {
    MESSAGE,
    FILE,
    IMG,
    ENTER,
    LEFT,
    SUB,
    DELETE_MESSAGE,
    ADD_EMOJI,
    REMOVE_EMOJI,
}

fun ChatType.isMessage(): Boolean =
    when (this) {
        ChatType.MESSAGE -> true
        ChatType.FILE -> true
        ChatType.IMG -> true
        ChatType.ENTER -> true
        ChatType.LEFT -> true
        else -> false
    }

fun ChatType.isEmoji(): Boolean =
    when (this) {
        ChatType.ADD_EMOJI -> true
        ChatType.REMOVE_EMOJI -> true
        else -> false
    }

fun ChatType.isSub(): Boolean =
    when (this) {
        ChatType.SUB -> true
        else -> false
    }

fun ChatType.isDeleteMessage(): Boolean =
    when (this) {
        ChatType.DELETE_MESSAGE -> true
        else -> false
    }