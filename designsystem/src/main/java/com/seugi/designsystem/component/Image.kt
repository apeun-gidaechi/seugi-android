package com.seugi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun SeugiImage(modifier: Modifier = Modifier, url: String, contentDescription: String? = null, colorFilter: ColorFilter? = null, alpha: Float = DefaultAlpha) {
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(model = url),
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        alpha = alpha,
    )
}

@Composable
fun SeugiImage(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
    alpha: Float = DefaultAlpha,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        alpha = alpha,
    )
}
