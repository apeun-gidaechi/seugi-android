package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.DropDownType
import com.seugi.designsystem.component.SeugiDropDown
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.toImmutableList

@Composable
fun Dropdown() {
    val dummyList = listOf("1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2")

    var selectedItem by remember { mutableStateOf("비밀번호 선택") }
    var isExpanded by remember { mutableStateOf(false) }

    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SeugiDropDown(
                item = dummyList.toImmutableList(),
                title = "비밀번호 선택",
                type = DropDownType.Disabled,
                onItemSelected = { selectedItem = it },
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon,
                onExpandedChanged = { isExpanded = it },
            )

            Spacer(modifier = Modifier.padding(horizontal = 20.dp))

            SeugiDropDown(
                item = dummyList.toImmutableList(),
                title = "비밀번호 선택",
                type = DropDownType.Typing,
                onItemSelected = { selectedItem = it },
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon,
                onExpandedChanged = { isExpanded = it },
            )
        }
    }
}
