package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun Shadow() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.white),
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .dropShadow(DropShadowType.EvBlack1)
                .background(SeugiTheme.colors.white),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .dropShadow(DropShadowType.EvBlack2)
                .background(SeugiTheme.colors.white),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .dropShadow(DropShadowType.EvBlack3)
                .background(SeugiTheme.colors.white),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .dropShadow(DropShadowType.Nav)
                .background(SeugiTheme.colors.white),
        )
    }
}
