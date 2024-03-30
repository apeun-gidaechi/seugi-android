package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.theme.Primary200
import com.apeun.gidaechi.designsystem.theme.Primary300
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

sealed class AvatarType(val size: Dp) {
    data object ExtraSmall: AvatarType(16.dp)
    data object Small: AvatarType(24.dp)
    data object Medium: AvatarType(32.dp)
    data object Large: AvatarType(36.dp)
    data object ExtraLarge: AvatarType(64.dp)
    data object XXL: AvatarType(128.dp)

}

/**
 * Seugi Avatar
 *
 * **Need Internet Permission**
 *
 * @param image the indicates the URL of the image; if not, the default image will be displayed.
 * @param type the indicates the size of the image.
 */
@Composable
fun SeugiAvatar(
    modifier: Modifier = Modifier,
    image: String? = null,
    type: AvatarType
) {
    if (image == null) {
        Box(
            modifier = modifier
                .size(type.size)
                .background(
                    color = Primary200,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(type.size/2),
                painter = painterResource(id = R.drawable.ic_person_fill),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Primary300)
            )
        }
    } else {
        AsyncImage(
            modifier = modifier
                .size(type.size)
                .clip(CircleShape),
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun PreviewSeugiAvatar() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                type = AvatarType.ExtraSmall
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Small
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Medium
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.Large
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.ExtraLarge
            )
            SeugiAvatar(
                image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_92x30dp.png",
                type = AvatarType.XXL
            )
        }
    }
}