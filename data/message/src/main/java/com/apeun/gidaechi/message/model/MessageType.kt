package com.apeun.gidaechi.message.model

enum class MessageType {
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

fun MessageType.isMessage(): Boolean =
    when (this) {
        MessageType.MESSAGE -> true
        MessageType.FILE -> true
        MessageType.IMG -> true
        MessageType.ENTER -> true
        MessageType.LEFT -> true
        else -> false
    }

fun MessageType.isEmoji(): Boolean =
    when (this) {
        MessageType.ADD_EMOJI -> true
        MessageType.REMOVE_EMOJI -> true
        else -> false
    }

fun MessageType.isSub(): Boolean =
    when (this) {
        MessageType.SUB -> true
        else -> false
    }

fun MessageType.isDeleteMessage(): Boolean =
    when (this) {
        MessageType.DELETE_MESSAGE -> true
        else -> false
    }