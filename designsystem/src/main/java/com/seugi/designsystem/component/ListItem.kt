package com.seugi.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray100
import com.seugi.designsystem.theme.Gray400
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White

sealed class ListItemType {
    data object Normal : ListItemType()
    data class Toggle(val checked: Boolean, val onCheckedChangeListener: (Boolean) -> Unit) : ListItemType()
    data class Description(val description: String) : ListItemType()
    data object Icon : ListItemType()
}

@Composable
fun SeugiListItem(modifier: Modifier = Modifier, type: ListItemType = ListItemType.Normal, text: String, onClick: () -> Unit) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) White else Gray100,
        label = "",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = animColor,
                shape = RoundedCornerShape(8.dp),
            )
            .bounceClick(
                onClick = onClick,
                onChangeButtonState = {
                    buttonState = it
                },
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 21.dp,
                )
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = Black,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            when (type) {
                is ListItemType.Toggle -> {
                    SeugiToggle(
                        checked = type.checked,
                        onCheckedChangeListener = type.onCheckedChangeListener,
                    )
                }
                is ListItemType.Description -> {
                    Text(
                        text = type.description,
                        color = Gray500,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                is ListItemType.Icon -> {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "오른쪽 방향표",
                        colorFilter = ColorFilter.tint(Gray400),
                    )
                }
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiListItem() {
    var checked by remember { mutableStateOf(false) }
    SeugiTheme {
        Column {
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Normal,
                text = "서비스정책",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Toggle(
                    checked = checked,
                    onCheckedChangeListener = {
                        checked = it
                    },
                ),
                text = "알람 설정",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Description("v1.0.0"),
                text = "버전",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Icon,
                text = "이동",
                onClick = {
                },
            )
        }
    }
}
