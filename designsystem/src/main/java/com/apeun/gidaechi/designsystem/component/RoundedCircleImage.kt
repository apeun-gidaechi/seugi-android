package com.apeun.gidaechi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

sealed class Size(val size: Dp, val radius: Dp) {
    data object Large : Size(180.dp, 64.dp)
    data object Small : Size(128.dp, 36.dp)
}

sealed class Type(@DrawableRes val image: Int) {
    data object Add : Type(R.drawable.ic_add)
    data object School : Type(R.drawable.ic_school)
    data object Image : Type(R.drawable.ic_image)
}

@Composable
fun SeugiRoundedCircleImage(modifier: Modifier = Modifier, size: Size, type: Type, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(size = size.size)
            .clip(shape = RoundedCornerShape(size.radius))
            .background(Gray100)
            .bounceClick(onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(size.size / 2),
            painter = painterResource(id = type.image),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Gray400),
        )
    }
}

@Composable
fun SeugiRoundedCircleImage(modifier: Modifier = Modifier, size: Size, image: String, onClick: () -> Unit) {
    AsyncImage(
        modifier = modifier
            .bounceClick(onClick)
            .size(size.size)
            .clip(shape = RoundedCornerShape(size.radius))
            ,
        model = image,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
private fun PreviewSeugiRoundedCircleImage() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.Add, onClick = {})
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, type = Type.Add, onClick = {})
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.School, onClick = {})
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, type = Type.School, onClick = {})
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.Image, onClick = {})
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, type = Type.Image, onClick = {})
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, image = "https://images-na.ssl-images-amazon.com/images/I/41VTLQ%2BH-oL._UL1200_.jpg", onClick = {})
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, image = "https://images-na.ssl-images-amazon.com/images/I/41VTLQ%2BH-oL._UL1200_.jpg", onClick = {})
            }
        }
    }
}
