package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray200
import com.apeun.gidaechi.designsystem.theme.Gray300
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

sealed class DropDownType(
    val textColor: Color,
    val backgroundColor: Color,
    val iconColor: Color,
) {
    data object Disabled : DropDownType(Gray400, Gray200, Gray400)
    data object Typing : DropDownType(Black, Gray300, Gray500)
}

@Composable
fun SeugiDropDown(item: List<String>, title: String, type: DropDownType) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(title)
    }
    val scrollState = rememberScrollState()

    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val clickableModifier = if (type != DropDownType.Disabled) {
        Modifier.clickable { isExpanded = !isExpanded }
    } else {
        Modifier
    }

    SeugiTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(12.dp))
                .then(clickableModifier),
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
                    color = if (selectedItem == title) Gray500 else Black,
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = type.iconColor,
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
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
                            selectedItem = label
                            isExpanded = false
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
@Preview(showBackground = true)
fun PreviewSeugiDropdown() {
    val dummyList = listOf("1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2")

    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SeugiDropDown(dummyList, "비밀번호 선택", DropDownType.Disabled)

            Spacer(modifier = Modifier.padding(horizontal = 20.dp))

            SeugiDropDown(dummyList, "비밀번호 선택", DropDownType.Typing)
        }
    }
}
