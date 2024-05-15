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
        ChatType.SUB -> false
        ChatType.DELETE_MESSAGE -> false
        ChatType.ADD_EMOJI -> false
        ChatType.REMOVE_EMOJI -> false
    }

fun ChatType.isEmoji(): Boolean =
    when (this) {
        ChatType.MESSAGE -> false
        ChatType.FILE -> false
        ChatType.IMG -> false
        ChatType.ENTER -> false
        ChatType.LEFT -> false
        ChatType.SUB -> false
        ChatType.DELETE_MESSAGE -> false
        ChatType.ADD_EMOJI -> true
        ChatType.REMOVE_EMOJI -> true
    }

fun ChatType.isSub(): Boolean =
    when (this) {
        ChatType.MESSAGE -> false
        ChatType.FILE -> false
        ChatType.IMG -> false
        ChatType.ENTER -> false
        ChatType.LEFT -> false
        ChatType.SUB -> true
        ChatType.DELETE_MESSAGE -> false
        ChatType.ADD_EMOJI -> false
        ChatType.REMOVE_EMOJI -> false
    }

fun ChatType.isDeleteMessage(): Boolean =
    when (this) {
        ChatType.MESSAGE -> false
        ChatType.FILE -> false
        ChatType.IMG -> false
        ChatType.ENTER -> false
        ChatType.LEFT -> false
        ChatType.SUB -> false
        ChatType.DELETE_MESSAGE -> true
        ChatType.ADD_EMOJI -> false
        ChatType.REMOVE_EMOJI -> false
    }