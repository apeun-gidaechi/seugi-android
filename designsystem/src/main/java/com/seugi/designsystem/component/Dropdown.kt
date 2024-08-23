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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed interface DropDownType {
    data object Disabled : DropDownType
    data object Typing : DropDownType
}

@Composable
fun SeugiDropDown(
    item: ImmutableList<String>,
    title: String,
    type: DropDownType,
    onItemSelected: (String) -> Unit,
    isExpanded: Boolean,
    selectedItem: String,
    icon: ImageVector,
    onExpandedChanged: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val dropDownColor = type.colors()

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
                .border(1.dp, dropDownColor.backgroundColor, RoundedCornerShape(12.dp))
                .background(SeugiTheme.colors.white, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),

        ) {
            Text(
                text = selectedItem,
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.titleMedium,
                color = when {
                    type == DropDownType.Disabled -> SeugiTheme.colors.gray200
                    selectedItem == title -> SeugiTheme.colors.gray500
                    else -> SeugiTheme.colors.black
                },
            )

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd),
                tint = dropDownColor.iconColor,
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
                    .background(SeugiTheme.colors.white)
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
                                color = dropDownColor.textColor,
                            )
                        },
                        modifier = Modifier.background(SeugiTheme.colors.white),
                    )
                }
            }
        }
    }
}

@Composable
fun SeugiSmallDropDown(
    modifier: Modifier = Modifier,
    item: ImmutableList<String>,
    onItemSelected: (String) -> Unit,
    isExpanded: Boolean,
    selectedItem: String,
    icon: ImageVector,
    onExpandedChanged: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .clickable(
                onClick = {
                    onExpandedChanged(!isExpanded)
                },
                interactionSource = NoInteractionSource(),
                indication = null,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedItem,
                modifier = Modifier
                    .wrapContentWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = SeugiTheme.colors.black,
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
                .background(SeugiTheme.colors.white)
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
                        .background(SeugiTheme.colors.white)
                        .wrapContentWidth(),
                )
            }
        }
    }
}

@Immutable
data class DropDownColor(
    val textColor: Color,
    val backgroundColor: Color,
    val iconColor: Color,
)

@Composable
private fun DropDownType.colors() = when (this) {
    is DropDownType.Disabled -> DropDownColor(
        textColor = SeugiTheme.colors.gray400,
        backgroundColor = SeugiTheme.colors.gray200,
        iconColor = SeugiTheme.colors.gray400,
    )
    is DropDownType.Typing -> DropDownColor(
        textColor = SeugiTheme.colors.black,
        backgroundColor = SeugiTheme.colors.gray300,
        iconColor = SeugiTheme.colors.gray500,
    )
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

            Spacer(modifier = Modifier.padding(horizontal = 20.dp))

            SeugiSmallDropDown(
                item = dummyList.toImmutableList(),
                onItemSelected = { selectedItem = it },
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon,
            ) {
                isExpanded = it
            }
        }
    }
}
