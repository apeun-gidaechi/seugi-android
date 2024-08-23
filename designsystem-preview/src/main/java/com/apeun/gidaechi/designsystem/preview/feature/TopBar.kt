package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    SeugiTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SeugiTopBar(
                title = {
                    Text(text = "로그인", style = SeugiTheme.typography.subtitle1)
                },
                onNavigationIconClick = {
                },
                actions = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_search,
                        ),
                        contentDescription = null,
                        modifier = Modifier.bounceClick(
                            {
                            },
                        ),
                    )
                    Spacer(modifier = Modifier.padding(end = 16.dp))
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_menu,
                        ),
                        contentDescription = null,
                        modifier = Modifier.bounceClick(
                            {
                            },
                        ),
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))
                },
                shadow = true,
            )
        }
    }
}
