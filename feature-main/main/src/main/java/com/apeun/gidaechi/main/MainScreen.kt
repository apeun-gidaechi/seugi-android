package com.apeun.gidaechi.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.designsystem.component.BottomNavigationItemType
import com.apeun.gidaechi.designsystem.component.SeugiBottomNavigation

@Composable
internal fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    var selectItemState: BottomNavigationItemType by remember { mutableStateOf(BottomNavigationItemType.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SeugiBottomNavigation(selected = selectItemState) {
                selectItemState = it
                navHostController.navigate(
                    when (it) {
                        is BottomNavigationItemType.Home -> "route"
                        is BottomNavigationItemType.Chat -> "route"
                        is BottomNavigationItemType.Group -> "route"
                        is BottomNavigationItemType.Notification -> "route"
                        is BottomNavigationItemType.Profile -> "route"
                        else -> "route"
                    },
                )
            }
        },
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navHostController,
            startDestination = "route",
        ) {
            // TODO("DELETE DUMMY ROUTE")
            composable("route") {
            }
        }
    }
}
