package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.White


@Composable
fun Shadow(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ) {
        Box(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .dropShadow(DropShadowType.Ev1)
            .background(White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .dropShadow(DropShadowType.Ev2)
            .background(White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .dropShadow(DropShadowType.Ev3)
            .background(White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .dropShadow(DropShadowType.Nav)
            .background(White)
        )
    }
}