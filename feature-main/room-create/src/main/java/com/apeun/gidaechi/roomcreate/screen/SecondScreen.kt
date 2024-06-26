package com.apeun.gidaechi.roomcreate.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.RoomImageType
import com.apeun.gidaechi.designsystem.component.SeugiRoomImage
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiTextField
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(placeholder: String, onNameSuccess: (String) -> Unit, popBackStack: () -> Unit) {
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                },
                backIconCheck = true,
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
                SeugiRoomImage(
                    type = RoomImageType.ExtraLarge,
                    text = placeholder.substring(0, 1),
                )
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd),
                    painter = painterResource(id = R.drawable.ic_add_fill),
                    contentDescription = "add_button",
                    colorFilter = ColorFilter.tint(Gray600),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "채팅방 이름",
                style = MaterialTheme.typography.titleMedium,
                color = Black,
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
