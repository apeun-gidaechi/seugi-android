package com.apeun.gidaechi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.Primary200
import com.apeun.gidaechi.designsystem.theme.Red100
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

sealed class Size(val size: Dp, val radius: Dp) {
    data object Large : Size(180.dp, 64.dp)
    data object Small : Size(128.dp, 36.dp)
}

sealed class Type(@DrawableRes val image: Int){
    data object Add: Type(R.drawable.ic_add)
    data object School: Type(R.drawable.ic_school)
    data object Image: Type(R.drawable.ic_image)
}


@Composable
fun SeugiRoundedCircleImage(
    modifier: Modifier = Modifier,
    size: Size,
    type: Type
) {
    Box(
        modifier = modifier
            .size(size = size.size)
            .clip(shape = RoundedCornerShape(size.radius))
            .background(Gray100)
            .bounceClick({}),
        contentAlignment = Alignment.Center
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
fun SeugiRoundedCircleImage(
    modifier: Modifier = Modifier,
    size: Size,
    image: String
) {
    AsyncImage(
        modifier = modifier
            .size(size.size)
            .clip(shape = RoundedCornerShape(size.radius))
            .bounceClick({}),
        model = image,
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.Add)
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, type = Type.Add)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.School)
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, type = Type.School)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, type = Type.Image)
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small,  type = Type.Image)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                SeugiRoundedCircleImage(size = Size.Large, image = "https://images-na.ssl-images-amazon.com/images/I/41VTLQ%2BH-oL._UL1200_.jpg")
                Spacer(modifier = Modifier.width(30.dp))
                SeugiRoundedCircleImage(size = Size.Small, image = "https://images-na.ssl-images-amazon.com/images/I/41VTLQ%2BH-oL._UL1200_.jpg")
            }
        }

    }
}