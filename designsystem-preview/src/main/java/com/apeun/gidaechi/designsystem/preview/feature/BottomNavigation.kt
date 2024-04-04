package com.apeun.gidaechi.designsystem.preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.apeun.gidaechi.designsystem.component.BottomNavigationItemType
import com.apeun.gidaechi.designsystem.component.SeugiBottomNavigation
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

@Composable
fun BottomNavigation() {
    var selectedItem: BottomNavigationItemType by remember { mutableStateOf(BottomNavigationItemType.Home) }
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SeugiBottomNavigation(
                modifier = Modifier,
                selected = selectedItem,
                onClick = {
                    selectedItem = it
                },
            )
        }
    }
}
