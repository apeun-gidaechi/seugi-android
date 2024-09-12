package com.seugi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.AvatarType
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiAvatar
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherProfileBottomSheet(
    modifier: Modifier = Modifier,
    profile: String?,
    name: String,
    status: String,
    spot: String,
    belong: String,
    phone: String,
    wire: String,
    location: String,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onClickChat: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        dragHandle = {}
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(SeugiTheme.colors.white)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SeugiAvatar(
                        type = AvatarType.Medium,
                        image = profile
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = name,
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SeugiButton(
                        onClick = onClickChat,
                        type = ButtonType.Black,
                        text = "채팅"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SeugiDivider(
                    type = DividerType.WIDTH,
                    size = 8.dp,
                    color = SeugiTheme.colors.gray100
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileCard(
                    title = "상태메세지",
                    content = status
                )
            }

            item {
                ProfileCard(
                    title = "직위",
                    content = spot
                )
            }

            item {
                ProfileCard(
                    title = "소속",
                    content = belong
                )
            }

            item {
                ProfileCard(
                    title = "휴대전화번호",
                    content = phone
                )
            }

            item {
                ProfileCard(
                    title = "유선전화번호",
                    content = wire
                )
            }

            item {
                ProfileCard(
                    title = "근무위치",
                    content = location
                )
            }
        }
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = title,
            style = SeugiTheme.typography.body1,
            color = SeugiTheme.colors.gray500
        )
        Spacer(modifier = Modifier.height(17.5.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = content,
            style = SeugiTheme.typography.subtitle2,
            color = SeugiTheme.colors.black
        )
        Spacer(modifier = Modifier.height(17.5.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 20.dp),
            type = DividerType.WIDTH
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}