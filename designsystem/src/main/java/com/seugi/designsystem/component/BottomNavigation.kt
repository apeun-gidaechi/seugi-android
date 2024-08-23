package com.seugi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import com.seugi.designsystem.R
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

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
            .height(62.dp)
            .dropShadow(DropShadowType.Nav)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(32.dp))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Home,
                selected = selected is BottomNavigationItemType.Home,
                isNew = false,
                onClick = {
                    onClick(BottomNavigationItemType.Home)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Chat,
                selected = selected is BottomNavigationItemType.Chat,
                isNew = false,
                onClick = {
                    onClick(BottomNavigationItemType.Chat)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Group,
                selected = selected is BottomNavigationItemType.Group,
                isNew = false,
                onClick = {
                    onClick(BottomNavigationItemType.Group)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Notification,
                selected = selected is BottomNavigationItemType.Notification,
                isNew = false,
                onClick = {
                    onClick(BottomNavigationItemType.Notification)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigationItem(
                type = BottomNavigationItemType.Profile,
                selected = selected is BottomNavigationItemType.Profile,
                isNew = false,
                onClick = {
                    onClick(BottomNavigationItemType.Profile)
                },
            )
            Spacer(modifier = Modifier.width(32.dp))
        }
    }
}

@Composable
private fun SeugiBottomNavigationItem(type: BottomNavigationItemType, selected: Boolean, isNew: Boolean, onClick: () -> Unit) {
    val animIconColor by animateColorAsState(
        targetValue = if (selected) SeugiTheme.colors.primary500 else SeugiTheme.colors.gray300,
        label = "",
    )
    val animTextColor by animateColorAsState(
        targetValue = if (selected) SeugiTheme.colors.primary500 else SeugiTheme.colors.gray500,
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
        Box {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = type.resId),
                contentDescription = "",
                colorFilter = ColorFilter.tint(animIconColor),
            )
            if (isNew) {
                SeugiBadge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(
                            x = 6.dp,
                            y = (-2).dp,
                        ),
                )
            }
        }
        Text(
            text = type.text,
            style = SeugiTheme.typography.caption2,
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
                .background(SeugiTheme.colors.white),
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
