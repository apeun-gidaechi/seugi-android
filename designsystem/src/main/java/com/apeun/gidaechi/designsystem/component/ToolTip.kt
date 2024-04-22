package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.theme.Gray700
import com.apeun.gidaechi.designsystem.theme.White

sealed class ToolTipType {
    data object Side : ToolTipType()
    data object SideSmall : ToolTipType()
    data object Center : ToolTipType()
    data object CenterSmall : ToolTipType()
}

@Composable
fun SeugiToolTip(text: String, type: ToolTipType) {
    Column {
        when (type) {
            is ToolTipType.Side -> {
                Image(
                    modifier = Modifier.padding(start = 16.dp),
                    painter = painterResource(id = R.drawable.ic_side_polygon),
                    contentDescription = "",
                )
            }
            is ToolTipType.Center -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_center_polygon),
                    contentDescription = "",
                )
            }
            is ToolTipType.SideSmall -> {
                Image(
                    modifier = Modifier.padding(start = 12.dp),
                    painter = painterResource(id = R.drawable.ic_side_small_polygon),
                    contentDescription = "",
                )
            }
            is ToolTipType.CenterSmall -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_center_samll_polygon),
                    contentDescription = "",
                )
            }
        }

        Box(
            modifier = Modifier
                .background(
                    color = Gray700,
                    shape = RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp,
                ),
                text = text,
                color = White,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
