package com.seugi.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.AlphaIndication
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiMemberList(onClick: () -> Unit, userName: String, userProfile: String?, isCrown: Boolean = false, crownColor: Color = SeugiTheme.colors.black, action: @Composable () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                indication = AlphaIndication,
                interactionSource = remember { MutableInteractionSource() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SeugiAvatar(
                type = AvatarType.Large,
                image = userProfile,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = userName,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            if (isCrown) {
                Spacer(modifier = Modifier.width(4.dp))
                SeugiImage(
                    modifier = Modifier.size(24.dp),
                    resId = R.drawable.ic_crown_fill,
                    colorFilter = ColorFilter.tint(color = crownColor),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            action()
        }
    }
}

@Composable
fun SeugiMemberList(
    modifier: Modifier = Modifier,
    userName: String,
    userProfile: String?,
    isCrown: Boolean = false,
    checked: Boolean = false,
    onCheckedChangeListener: (Boolean) -> Unit = {},
    crownColor: Color = SeugiTheme.colors.black,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = {
                    onCheckedChangeListener(!checked)
                },
                role = Role.Button,
                indication = AlphaIndication,
                interactionSource = remember { MutableInteractionSource() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SeugiAvatar(
                type = AvatarType.Large,
                image = userProfile,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = userName,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            if (isCrown) {
                Spacer(modifier = Modifier.width(4.dp))
                SeugiImage(
                    modifier = Modifier.size(24.dp),
                    resId = R.drawable.ic_crown_fill,
                    colorFilter = ColorFilter.tint(color = crownColor),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            SeugiCheckbox(
                type = CheckBoxType.Large,
                checked = checked,
            ) {
                onCheckedChangeListener(it)
            }
        }
    }
}

@Composable
fun SeugiMemberList(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                indication = AlphaIndication,
                interactionSource = remember { MutableInteractionSource() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 14.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_add_ring_line),
                contentDescription = "icon add ring line",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.primary400),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.primary400,
            )
        }
    }
}

@Composable
fun SeugiMemberList(
    modifier: Modifier = Modifier,
    userName: String,
    userProfile: String?,
    isCrown: Boolean = false,
    content: @Composable () -> Unit = {},
    crownColor: Color = SeugiTheme.colors.black,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SeugiAvatar(
                type = AvatarType.Large,
                image = userProfile,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = userName,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            if (isCrown) {
                Spacer(modifier = Modifier.width(4.dp))
                SeugiImage(
                    modifier = Modifier.size(24.dp),
                    resId = R.drawable.ic_crown_fill,
                    colorFilter = ColorFilter.tint(color = crownColor),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiMemberList() {
    var checked by remember { mutableStateOf(true) }
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiMemberList(
                onClick = {},
                userName = "노영재",
                userProfile = null,
                isCrown = true,
                crownColor = SeugiTheme.colors.yellow500,
            )
            SeugiMemberList(
                userName = "노영재",
                userProfile = null,
                isCrown = true,
                checked = checked,
                onCheckedChangeListener = {
                    checked = it
                },
                crownColor = SeugiTheme.colors.orange500,
            )
            SeugiMemberList(
                text = "멤버 초대하기",
                onClick = {
                },
            )
        }
    }
}
