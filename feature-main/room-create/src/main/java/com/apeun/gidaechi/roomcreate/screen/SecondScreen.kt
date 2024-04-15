package com.apeun.gidaechi.roomcreate.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiTextField
import com.apeun.gidaechi.designsystem.theme.Black

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
            modifier = Modifier.padding(paddingValue),
        ) {
            SeugiTextField(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                    ),
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
