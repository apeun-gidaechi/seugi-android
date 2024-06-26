package com.apeun.gidaechi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apeun.gidaechi.designsystem.component.RoomImageType
import com.apeun.gidaechi.designsystem.component.SeugiRoomImage

@Composable
fun RoomImage() {
    val text = "엄준식"
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SeugiRoomImage(
            text = text,
            type = RoomImageType.ExtraSmall,
        )
        SeugiRoomImage(
            text = text,
            type = RoomImageType.Small,
        )
        SeugiRoomImage(
            text = text,
            type = RoomImageType.Medium,
        )
        SeugiRoomImage(
            text = text,
            type = RoomImageType.Large,
        )
        SeugiRoomImage(
            text = text,
            type = RoomImageType.ExtraLarge,
        )
        SeugiRoomImage(
            text = text,
            type = RoomImageType.XXL,
        )
    }
}
