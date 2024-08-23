package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onNavigationIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    containerColors: Color = SeugiTheme.colors.white,
    shadow: Boolean = false,
) {
    val modifierWithShadow = if (shadow) {
        modifier.dropShadow(DropShadowType.EvBlack1)
    } else {
        modifier
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(containerColors)
            .then(modifierWithShadow),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        if (onNavigationIconClick != null) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_back,
                ),
                contentDescription = null,
                modifier = Modifier
                    .bounceClick(onClick = onNavigationIconClick),
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        title()
        Spacer(modifier = Modifier.weight(1f))
        actions()
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSeugiTopBar() {
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
                    Spacer(modifier = Modifier.padding(end = 16.dp))
                },
                shadow = true,
            )
        }
    }
}
