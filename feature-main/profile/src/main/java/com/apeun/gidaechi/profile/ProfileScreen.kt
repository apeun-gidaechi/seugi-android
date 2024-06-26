package com.apeun.gidaechi.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R.drawable
import com.apeun.gidaechi.designsystem.component.AvatarType
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.SeugiAvatar
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        SeugiTopBar(
            title = {
                Text(
                    text = "내 프로필",
                    style = MaterialTheme.typography.titleLarge,
                    color = Black
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            SeugiAvatar(type = AvatarType.Medium)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "노영재",
                style = MaterialTheme.typography.titleMedium,
                color = Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(32.dp),
                painter = painterResource(id = drawable.ic_setting_fill),
                contentDescription = "설정 톱니바퀴",
                colorFilter = ColorFilter.tint(Gray500)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        SeugiDivider(
            size = 8.dp,
            type = DividerType.WIDTH
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "상태메세지",
            content = "대소고 어딘가",
            onClickEdit = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "직위",
            content = "제갈 여친",
            onClickEdit = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "소속",
            content = "대소고 어딘가",
            onClickEdit = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "휴대전화번호",
            content = "010-1234-5678",
            onClickEdit = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "유선전화번호",
            content = "02-1234-5678",
            onClickEdit = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

    }
}

@Composable
internal fun ProfileCard(
    title: String,
    content: String,
    onClickEdit: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Gray500
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = drawable.ic_write_line),
                contentDescription = "수정하기 아이콘",
                colorFilter = ColorFilter.tint(Gray500)
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp),
                text = content,
                style = MaterialTheme.typography.titleMedium,
                color = Black
            )
        }
    }
}