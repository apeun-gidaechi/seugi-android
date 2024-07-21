package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.theme.Primary200
import com.seugi.designsystem.theme.Primary700
import com.seugi.designsystem.theme.SeugiTextTheme

sealed class RoomImageType(
    val size: Dp,
    val textStyle: SeugiTextTheme,
) {

    data object ExtraSmall : RoomImageType(
        size = 16.dp,
        textStyle = SeugiTextTheme.LabelMedium,
    )
    data object Small : RoomImageType(
        size = 24.dp,
        textStyle = SeugiTextTheme.LabelLarge,
    )
    data object Medium : RoomImageType(
        size = 32.dp,
        textStyle = SeugiTextTheme.BodyLarge,
    )
    data object Large : RoomImageType(
        size = 36.dp,
        textStyle = SeugiTextTheme.TitleLarge,
    )
    data object ExtraLarge : RoomImageType(
        size = 64.dp,
        textStyle = SeugiTextTheme.HeadlineLarge,
    )
    data object XXL : RoomImageType(
        size = 128.dp,
        textStyle = SeugiTextTheme.DisplayLarge,
    )
}

@Composable
fun SeugiRoomImage(modifier: Modifier = Modifier, text: String = "", type: RoomImageType) {
    Box(
        modifier = modifier
            .size(type.size)
            .clip(CircleShape)
            .background(
                color = Primary200,
                shape = CircleShape,
            ),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (text.isEmpty()) "" else text.substring(startIndex = 0, endIndex = 1),
            color = Primary700,
            style = type.textStyle.toTextStyle(),
        )
    }
}
