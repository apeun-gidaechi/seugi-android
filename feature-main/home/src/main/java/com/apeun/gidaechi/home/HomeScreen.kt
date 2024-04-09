package com.apeun.gidaechi.home

import android.app.Activity
import android.graphics.Color.toArgb
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleResumeEffect
import coil.compose.AsyncImage
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.SeugiAvatar
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.component.SeugiDropDown
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.White

@Composable
internal fun HomeScreen(

) {
    val view = LocalView.current

    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                changeNavigationColor(window, White, false)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            changeNavigationColor(window, Primary050, false)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary050),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier
                    .padding(
                        vertical = (10).dp
                    )
                    .clickable(
                        onClick = {},
                        role = Role.Button
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = "https://avatars.githubusercontent.com/u/163500498?s=48&v=4",
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "대구 소프트웨어 마이스터 고등학교",
                    color = Gray600,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_expand_down_line),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Gray600)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        SeugiDivider(type = DividerType.WIDTH)
    }
}

private fun changeNavigationColor(
    window: Window,
    backgroundColor: Color,
    isDark: Boolean
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = backgroundColor.toArgb()
        window.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = if(isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}