package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.seugi.designsystem.component.SeugiError
import com.seugi.designsystem.preview.R

@Composable
fun Error() {
    Column {
        SeugiError(
            resId = com.seugi.designsystem.R.drawable.ic_emoji_sad,
            text = "페이지를 찾을 수 없습니다.",
        )
        SeugiError(
            resId = com.seugi.designsystem.R.drawable.ic_emoji_happy,
            text = "페이지를 찾을 수 없습니다.",
        )
    }
}
