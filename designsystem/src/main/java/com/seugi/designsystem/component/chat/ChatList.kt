package com.seugi.designsystem.component.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.component.AvatarType
import com.seugi.designsystem.component.SeugiAvatar
import com.seugi.designsystem.component.SeugiBadge
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiChatList(
    modifier: Modifier = Modifier,
    userName: String,
    userProfile: String? = null,
    message: String,
    createdAt: String,
    memberCount: Int? = null,
    count: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = NoInteractionSource(),
                indication = null,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SeugiAvatar(
                    type = AvatarType.Large,
                    image = userProfile,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = userName,
                            style = SeugiTheme.typography.subtitle2,
                            color = SeugiTheme.colors.black,
                        )
                        if (memberCount != null) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = memberCount.toString(),
                                style = SeugiTheme.typography.body2,
                                color = SeugiTheme.colors.gray500,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = message,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = SeugiTheme.typography.body2,
                        color = SeugiTheme.colors.black,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = createdAt,
                    style = SeugiTheme.typography.body2,
                    color = SeugiTheme.colors.gray500,
                )
                if (count != null && count != 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    SeugiBadge(
                        count = count,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiChatList() {
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SeugiChatList(
                userName = "노영재",
                message = "나 사실..",
                createdAt = "12:39",
                onClick = {
                },
            )
            SeugiChatList(
                userName = "노영재",
                message = "나 사실..",
                createdAt = "12:39",
                count = 72,
                onClick = {},
            )
            SeugiChatList(
                userName = "노영재너럴캡숑짱ㅋ",
                message = "https://seugi.s3.ap-northeast-2.amazonaws.com/IMG/c10f85d3-5ce0-4bfd-887e-6aed0a8100b9-file",
                createdAt = "12:39",
                memberCount = 4,
                onClick = {},
            )
            SeugiChatList(
                userName = "노영재너럴캡숑짱ㅋ",
                message = "정말 좋습니다",
                createdAt = "12:39",
                count = 72,
                memberCount = 4,
                onClick = {},
            )
        }
    }
}
