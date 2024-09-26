package com.seugi.chatdatail.model

import android.graphics.Bitmap
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed class ChatLocalType(
    open val uuid: String
) {
    data class FailedText(val text: String, override val uuid: String): ChatLocalType(uuid)
    data class SendText(val text: String, override val uuid: String): ChatLocalType(uuid)

    data class FailedImgUpload(val image: Bitmap, val fileName: String, override val uuid: String): ChatLocalType(uuid)

    data class FailedImgSend(val image: String, val fileName: String, override val uuid: String): ChatLocalType(uuid)

    data class SendImg(val image: Bitmap, val fileName: String, override val uuid: String): ChatLocalType(uuid)

    data class SendImgUrl(val image: String, val fileName: String, override val uuid: String): ChatLocalType(uuid)

    data class FailedFileUpload(val fileByteArray: ByteArray, val fileName: String, val fileByte: Long, val fileMimeType: String, override val uuid: String): ChatLocalType(uuid) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FailedFileUpload

            if (!fileByteArray.contentEquals(other.fileByteArray)) return false
            if (fileName != other.fileName) return false
            if (fileByte != other.fileByte) return false
            if (fileMimeType != other.fileMimeType) return false
            if (uuid != other.uuid) return false

            return true
        }

        override fun hashCode(): Int {
            var result = fileByteArray.contentHashCode()
            result = 31 * result + fileName.hashCode()
            result = 31 * result + fileByte.hashCode()
            result = 31 * result + fileMimeType.hashCode()
            result = 31 * result + uuid.hashCode()
            return result
        }
    }

    data class FailedFileSend(val fileUrl: String, val fileName: String, val fileByte: Long, override val uuid: String): ChatLocalType(uuid)

    data class SendFile(val fileByteArray: ByteArray, val fileName: String, val fileByte: Long, val fileMimeType: String, override val uuid: String): ChatLocalType(uuid) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FailedFileUpload

            if (!fileByteArray.contentEquals(other.fileByteArray)) return false
            if (fileName != other.fileName) return false
            if (fileByte != other.fileByte) return false
            if (fileMimeType != other.fileMimeType) return false
            if (uuid != other.uuid) return false

            return true
        }

        override fun hashCode(): Int {
            var result = fileByteArray.contentHashCode()
            result = 31 * result + fileName.hashCode()
            result = 31 * result + fileByte.hashCode()
            result = 31 * result + fileMimeType.hashCode()
            result = 31 * result + uuid.hashCode()
            return result
        }
    }

    data class SendFileUrl(val fileUrl: String, val fileName: String, val fileByte: Long, override val uuid: String): ChatLocalType(uuid)
}

internal fun Collection<ChatLocalType>.containsWithUUID(uuid: String): Boolean {
    for (i in this) {
        if (i.uuid == uuid) {
            return true
        }
    }
    return false
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

internal operator fun ImmutableList<ChatLocalType>.minus(uuid: String): ImmutableList<ChatLocalType> {
    return this.toMutableList().apply {
        removeIf {
            it.uuid == uuid
        }
    }.toImmutableList()
}