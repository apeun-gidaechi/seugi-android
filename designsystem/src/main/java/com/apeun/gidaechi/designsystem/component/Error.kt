package com.apeun.gidaechi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun SeugiError(modifier: Modifier = Modifier, @DrawableRes resId: Int, text: String) {
    Column(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = resId),
            contentDescription = "",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Black,
        )
    }
}

@Composable
fun SeugiError(modifier: Modifier, model: String, text: String) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally),
            model = model,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Black,
        )
    }
}

@Preview
@Composable
private fun PreviewSeugiError() {
    SeugiTheme {
        Column {
            SeugiError(
                resId = com.apeun.gidaechi.designsystem.R.drawable.ic_emoji_sad,
                text = "페이지를 찾을 수 없습니다.",
            )
            SeugiError(
                resId = com.apeun.gidaechi.designsystem.R.drawable.ic_emoji_happy,
                text = "페이지를 찾을 수 없습니다.",
            )
        }
    }
}
