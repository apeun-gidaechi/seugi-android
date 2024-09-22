package com.seugi.ui

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
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

fun ContentResolver.uriToBitmap(uri: Uri): Bitmap =
    ImageDecoder.decodeBitmap(
        ImageDecoder.createSource(this, uri)
    )