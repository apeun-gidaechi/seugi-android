package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.AvatarType
import com.apeun.gidaechi.designsystem.component.SeugiAvatar
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun Avatar() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            SeugiAvatar(type = AvatarType.ExtraSmall)
            SeugiAvatar(type = AvatarType.Small)
            SeugiAvatar(type = AvatarType.Medium)
            SeugiAvatar(type = AvatarType.Large)
            SeugiAvatar(type = AvatarType.ExtraLarge)
            SeugiAvatar(type = AvatarType.XXL)
            Spacer(modifier = Modifier.height(10.dp))
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.ExtraSmall,
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Small,
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Medium,
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Large,
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.ExtraLarge,
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.XXL,
            )
        }
    }
}