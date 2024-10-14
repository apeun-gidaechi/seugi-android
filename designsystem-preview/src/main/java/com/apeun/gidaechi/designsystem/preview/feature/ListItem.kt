package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.ListItemType
import com.seugi.designsystem.component.SeugiListItem
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun ListItem() {
    var checked by remember { mutableStateOf(false) }
    SeugiTheme {
        Column {
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Normal,
                text = "서비스정책",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Toggle(
                    checked = checked,
                    onCheckedChangeListener = {
                        checked = it
                    },
                ),
                text = "알람 설정",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SeugiListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = ListItemType.Description("v1.0.0"),
                text = "버전",
                onClick = {
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
