package com.apeun.gidaechi.designsystem.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeugiTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        scrolledContainerColor = MaterialTheme.colorScheme.surface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    backIconCheck: Boolean = false,
    shadow: Boolean = false,
) {
    val modifierWithShadow = if (shadow) {
        modifier.dropShadow(DropShadowType.Ev1)
    } else {
        modifier
    }
    TopAppBar(
        title = {
            CompositionLocalProvider(
                LocalContentColor provides colors.titleContentColor,
                LocalTextStyle provides MaterialTheme.typography.titleMedium
                    .copy(fontWeight = FontWeight.SemiBold),
                content = title,
            )
        },
        modifier = Modifier.then(
            modifierWithShadow,
        ),
        navigationIcon = {
            if (backIconCheck) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_back,
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .bounceClick(onClick = onNavigationIconClick)
                        .padding(start = 12.dp, end = 16.dp),
                )
            }
        },
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSeugiTopBar() {
    SeugiTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SeugiTopBar(
                title = {
                    Text(text = "로그인", style = MaterialTheme.typography.titleLarge)
                },
                onNavigationIconClick = {
                    Log.d("TAG", "backClick: ")
                },
                actions = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_search,
                        ),
                        contentDescription = null,
                        modifier = Modifier.bounceClick(
                            {
                                Log.d("TAG", "menuClick: ")
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
                                Log.d("TAG", "SearchClick: ")
                            },
                        ),
                    )
                    Spacer(modifier = Modifier.padding(end = 12.dp))
                },
                backIconCheck = true,
                shadow = true,
            )
        }
    }
}
