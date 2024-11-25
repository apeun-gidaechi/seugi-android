package com.seugi.ui

import android.app.DownloadManager
import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Patterns
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.File

fun changeNavigationColor(window: Window, backgroundColor: Color, isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = backgroundColor.toArgb()
        window.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

data class ExKeyboardState(
    val isOpen: Boolean = false,
    val height: Dp = 0.dp,
)

fun View.isKeyboardOpen(): Pair<Boolean, Int> {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return Pair(keypadHeight > screenHeight * 0.15, screenHeight - rect.bottom)
}

@Composable
fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val view = LocalView.current
    val density = LocalDensity.current

    fun Pair<Boolean, Int>.toState() = ExKeyboardState(
        isOpen = first,
        height = with(density) { second.toDp() - 48.dp },
    )

    return produceState(initialValue = view.isKeyboardOpen().toState()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            value = view.isKeyboardOpen().toState()
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}

fun downloadFile(context: Context, url: String, name: String, extension: String? = null) {
    try {
        // 웹 url인지 유효성 검사
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            return
        }

        val mimeType = getFileMimeType(extension ?: name.substringAfterLast(".", ""))

        val downloadManager = DownloadManager.Request(Uri.parse(url))
        val filePath = "/seugi/$name"
        // 알림 설정
        downloadManager
            .setTitle(filePath)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setMimeType(mimeType)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                filePath,
            )

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(downloadManager)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun checkFileExist(fileName: String): Boolean {
    val file = getFile(fileName)
    return file.isFile
}

fun getFile(fileName: String): File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/seugi/" + fileName)

fun getFileMimeType(fileName: String): String {
    val extension = fileName.substringAfterLast(".", "")
    return when (extension) {
        "jpg", "png", "jpeg", "gif", "bmp", "webp" -> "image/*"
        "plane", "html", "css", "javascript", "txt", "json", "csv" -> "text/*"
        "midi", "mpeg", "wav", "mp3" -> "audio/*"
        "webm", "mp4", "ogg", "avi", "mkv", "flv", "m4p", "m4v" -> "video/*"
        "apk" -> "application/vnd.android.package-archive"
        else -> "application/$extension"
    }
}

class EmojiUtiles {

    companion object {

        /**
         * 1~8 사이의 숫자만 허용합니다.
         * throws IllegalArgumentException
         * return String Emoji
         */
        fun getEmoji(id: Int): String = when(id) {
            1 -> "\uD83D\uDC4D"
            2 -> "\uD83D\uDC4C"
            3 -> "\uD83D\uDC4F"
            4 -> "\uD83D\uDE0D"
            5 -> "\uD83D\uDE06"
            6 -> "\uD83D\uDE32"
            7 -> "\uD83D\uDE22"
            8 -> "\uD83D\uDE24"
            else -> throw IllegalArgumentException("올바르지 않은 이모지.")
        }

        /**
         * EmojiUtiles.EMOJIS 에 존재하는 이모지만 허용합니다.
         * throws IllegalArgumentException
         * return Int EmojiIndex
         */
        fun emojiStringToId(emoji: String): Int {
            if (emoji !in EMOJIS) throw IllegalArgumentException("올바르지 않은 이모지.")
            return when (emoji) {
                "\uD83D\uDC4D" -> 1
                "\uD83D\uDC4C" -> 2
                "\uD83D\uDC4F" -> 3
                "\uD83D\uDE0D" -> 4
                "\uD83D\uDE06" -> 5
                "\uD83D\uDE32" -> 6
                "\uD83D\uDE22" -> 7
                "\uD83D\uDE24" -> 8
                else -> throw IllegalArgumentException("올바르지 않은 이모지.")
            }
        }

        val EMOJIS = listOf("\uD83D\uDC4D", "\uD83D\uDC4C", "\uD83D\uDC4F", "\uD83D\uDE0D", "\uD83D\uDE06", "\uD83D\uDE32", "\uD83D\uDE22", "\uD83D\uDE24")
    }
}