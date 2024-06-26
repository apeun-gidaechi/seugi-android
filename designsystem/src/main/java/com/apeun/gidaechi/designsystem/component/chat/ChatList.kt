package com.apeun.gidaechi.designsystem.component.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.component.AvatarType
import com.apeun.gidaechi.designsystem.component.SeugiAvatar
import com.apeun.gidaechi.designsystem.component.SeugiBadge
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

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
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
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
                            style = MaterialTheme.typography.titleMedium,
                            color = Black,
                        )
                        if (memberCount != null) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = memberCount.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray500,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = message,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = createdAt,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray500,
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
                message = "정말 좋습니다",
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
