package com.apeun.gidaechi.designsystem.component

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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.AlphaIndication
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Primary400
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@Composable
fun SeugiMemberList(userName: String, userProfile: String?, onClick: () -> Unit) {
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
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
        }
    }
}

@Composable
fun SeugiMemberList(modifier: Modifier = Modifier, userName: String, userProfile: String?, checked: Boolean = false, onCheckedChangeListener: (Boolean) -> Unit = {}) {
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
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
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
                colorFilter = ColorFilter.tint(Primary400),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = Primary400,
            )
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
                userName = "노영재",
                userProfile = null,
                onClick = {
                },
            )
            SeugiMemberList(
                userName = "노영재",
                userProfile = null,
                checked = checked,
                onCheckedChangeListener = {
                    checked = it
                },
            )
            SeugiMemberList(
                text = "멤버 초대하기",
                onClick = {
                },
            )
        }
    }
}
