package com.seugi.roomcreate.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.chat.ChatRoomType
import com.seugi.designsystem.component.chat.SeugiChatRoom
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SecondScreen(placeholder: String, onNameSuccess: (String) -> Unit, popBackStack: () -> Unit) {
    var title by remember { mutableStateOf("") }

    BackHandler(
        onBack = popBackStack,
    )
    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {},
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(
                                role = Role.Button,
                                onClick = {
                                    onNameSuccess(if (title == "") placeholder else title)
                                },
                            ),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 9.dp,
                                ),
                            text = "완료",
                            style = SeugiTheme.typography.body2,
                            color = SeugiTheme.colors.black,
                        )
                    }
                },
                onNavigationIconClick = popBackStack,
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(
                    horizontal = 20.dp,
                ),
        ) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                SeugiChatRoom(
                    type = ChatRoomType.ExtraLarge,
                    text = placeholder.substring(0, 1),
                )
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd),
                    painter = painterResource(id = R.drawable.ic_add_fill),
                    contentDescription = "add_button",
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "채팅방 이름",
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            Spacer(modifier = Modifier.height(4.dp))
            SeugiTextField(
                value = title,
                placeholder = placeholder,
                onValueChange = {
                    title = it
                },
                onClickDelete = {
                    title = ""
                },
            )
        }
    }
}
