package com.apeun.gidaechi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Gray300
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

sealed class BottomNavigationItemType(@DrawableRes val resId: Int, val text: String) {
    data object Home : BottomNavigationItemType(R.drawable.ic_home_fill, "홈")
    data object Chat : BottomNavigationItemType(R.drawable.ic_chat_fill, "채팅")
    data object Group : BottomNavigationItemType(R.drawable.ic_people_fill, "단체")
    data object Notification : BottomNavigationItemType(R.drawable.ic_notification_fill, "알림")
    data object Profile : BottomNavigationItemType(R.drawable.ic_person_fill, "프로필")
}

@Composable
fun SeugiBottomNavigation(modifier: Modifier = Modifier, selected: BottomNavigationItemType, onClick: (BottomNavigationItemType) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .dropShadow(DropShadowType.Nav)
            .background(
                color = White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(32.dp))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Home,
                selected = selected is BottomNavigationItemType.Home,
                onClick = {
                    onClick(BottomNavigationItemType.Home)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Chat,
                selected = selected is BottomNavigationItemType.Chat,
                onClick = {
                    onClick(BottomNavigationItemType.Chat)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Group,
                selected = selected is BottomNavigationItemType.Group,
                onClick = {
                    onClick(BottomNavigationItemType.Group)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Notification,
                selected = selected is BottomNavigationItemType.Notification,
                onClick = {
                    onClick(BottomNavigationItemType.Notification)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Profile,
                selected = selected is BottomNavigationItemType.Profile,
                onClick = {
                    onClick(BottomNavigationItemType.Profile)
                },
            )
            Spacer(modifier = Modifier.width(32.dp))
        }
    }
}

@Composable
private fun SeugiBottomNavigationItem(type: BottomNavigationItemType, selected: Boolean, onClick: () -> Unit) {
    val animIconColor by animateColorAsState(
        targetValue = if (selected) Primary500 else Gray300,
        label = "",
    )
    val animTextColor by animateColorAsState(
        targetValue = if (selected) Primary500 else Gray500,
        label = "",
    )

    Column(
        modifier = Modifier
            .width(32.dp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = type.resId),
            contentDescription = "",
            colorFilter = ColorFilter.tint(animIconColor),
        )
        Text(
            text = type.text,
            style = MaterialTheme.typography.labelMedium,
            color = animTextColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiBottomNavigation() {
    var selectedItem: BottomNavigationItemType by remember { mutableStateOf(BottomNavigationItemType.Home) }
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigation(
                modifier = Modifier,
                selected = selectedItem,
                onClick = {
                    selectedItem = it
                },
            )
        }
    }
}
