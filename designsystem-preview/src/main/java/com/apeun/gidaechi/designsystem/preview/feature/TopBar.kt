package com.apeun.gidaechi.designsystem.preview.feature

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
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
                            id = com.apeun.gidaechi.designsystem.R.drawable.ic_search,
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
                            id = com.apeun.gidaechi.designsystem.R.drawable.ic_menu,
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
