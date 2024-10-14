package com.seugi.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.flow.Flow

@Composable
fun <SIDE_EFFECT : Any> Flow<SIDE_EFFECT>.CollectAsSideEffect(lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)) {
    val sideEffectFlow = this
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect {
                sideEffect(it)
            }
        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

fun Context.shortToast(text: String?) {
    Toast.makeText(this, text ?: "", Toast.LENGTH_SHORT).show()
}

fun ContentResolver.uriToBitmap(uri: Uri): Bitmap = ImageDecoder.decodeBitmap(
    ImageDecoder.createSource(this, uri),
)

fun Bitmap.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(format, 100, stream)
    val byteArray = stream.toByteArray()
    return byteArray
}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? = this.query(uri, null, null, null, null)
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.lastPathSegment
    }
    return result
}

fun ContentResolver.getMimeType(uri: Uri): String? {
    val mimeType = this.getType(uri) ?: run {
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    return mimeType
}

fun ContentResolver.getUriByteArray(uri: Uri): ByteArray = this.openInputStream(uri)?.readBytes() ?: ByteArray(0)

fun ContentResolver.getFileSize(uri: Uri): Long {
    var fileSize: Long = -1
    val cursor: Cursor? = query(uri, null, null, null, null)
    cursor?.use {
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (sizeIndex != -1) {
            cursor.moveToFirst()
            fileSize = cursor.getLong(sizeIndex)
        }
    }
    return fileSize
}
