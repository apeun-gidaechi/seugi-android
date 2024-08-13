package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray200
import com.seugi.designsystem.theme.Gray300
import com.seugi.designsystem.theme.Gray400
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White

sealed class DropDownType(
    val textColor: Color,
    val backgroundColor: Color,
    val iconColor: Color,
) {
    data object Disabled : DropDownType(Gray400, Gray200, Gray400)
    data object Typing : DropDownType(Black, Gray300, Gray500)
}

@Composable
fun SeugiDropDown(
    item: List<String>,
    title: String,
    type: DropDownType,
    onItemSelected: (String) -> Unit,
    isExpanded: Boolean,
    selectedItem: String,
    icon: ImageVector,
    onExpandedChanged: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                enabled = type != DropDownType.Disabled,
                onClick = {
                    onExpandedChanged(!isExpanded)
                },
            ),
    ) {
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(52.dp)
                .border(1.dp, type.backgroundColor, RoundedCornerShape(12.dp))
                .background(White, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),

        ) {
            Text(
                text = selectedItem,
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.titleMedium,
                color = when {
                    type == DropDownType.Disabled -> Gray200
                    selectedItem == title -> Gray500
                    else -> Black
                },
            )

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd),
                tint = type.iconColor,
            )
        }

        if (type != DropDownType.Disabled) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    onExpandedChanged(false)
                },
                modifier = Modifier
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(White)
                    .heightIn(max = 240.dp),
                scrollState = scrollState,
            ) {
                item.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(label)
                            onExpandedChanged(false)
                        },
                        text = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.titleMedium,
                                color = type.textColor,
                            )
                        },
                        modifier = Modifier.background(White),
                    )
                }
            }
        }
    }
}

@Composable
fun SeugiSmallDropDown(
    item: List<String>,
    title: String,
    onItemSelected: (String) -> Unit,
    isExpanded: Boolean,
    selectedItem: String,
    icon: ImageVector,
    onExpandedChanged: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onExpandedChanged(!isExpanded)
                },
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = selectedItem,
                modifier = Modifier
                    .wrapContentWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                onExpandedChanged(false)
            },
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
                .background(White)
                .heightIn(max = 240.dp),
            scrollState = scrollState,
        ) {
            item.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(label)
                        onExpandedChanged(false)
                    },
                    text = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    modifier = Modifier
                        .background(White)
                        .wrapContentWidth(),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewSeugiDropdown() {
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
                item = dummyList,
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
                item = dummyList,
                title = "비밀번호 선택",
                type = DropDownType.Typing,
                onItemSelected = { selectedItem = it },
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon,
                onExpandedChanged = { isExpanded = it },
            )

            Spacer(modifier = Modifier.padding(horizontal = 20.dp))

            SeugiSmallDropDown(
                item = dummyList,
                title = "전체",
                onItemSelected = {selectedItem = it},
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon
            ) {
                isExpanded = it
            }
        }
    }
}
