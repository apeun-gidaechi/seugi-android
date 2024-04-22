package com.apeun.gidaechi.designsystem.preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.designsystem.preview.feature.Avatar
import com.apeun.gidaechi.designsystem.preview.feature.Badge
import com.apeun.gidaechi.designsystem.preview.feature.BottomNavigation
import com.apeun.gidaechi.designsystem.preview.feature.Button
import com.apeun.gidaechi.designsystem.preview.feature.Category
import com.apeun.gidaechi.designsystem.preview.feature.ChatItem
import com.apeun.gidaechi.designsystem.preview.feature.ChatList
import com.apeun.gidaechi.designsystem.preview.feature.CheckBox
import com.apeun.gidaechi.designsystem.preview.feature.Dialog
import com.apeun.gidaechi.designsystem.preview.feature.Divider
import com.apeun.gidaechi.designsystem.preview.feature.Dropdown
import com.apeun.gidaechi.designsystem.preview.feature.HomeScreen
import com.apeun.gidaechi.designsystem.preview.feature.ListItem
import com.apeun.gidaechi.designsystem.preview.feature.MemberList
import com.apeun.gidaechi.designsystem.preview.feature.OAuthButton
import com.apeun.gidaechi.designsystem.preview.feature.Shadow
import com.apeun.gidaechi.designsystem.preview.feature.Toggle
import com.apeun.gidaechi.designsystem.preview.feature.ToolTip
import com.apeun.gidaechi.designsystem.preview.feature.TopBar
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            SeugiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White,
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = NavRoot.HOME,
                    ) {
                        composable(NavRoot.HOME) {
                            HomeScreen(navController = navHostController)
                        }
                        composable(NavRoot.CHATITEM) {
                            ChatItem()
                        }
                        composable(NavRoot.CHATLIST) {
                            ChatList()
                        }
                        composable(NavRoot.AVATAR) {
                            Avatar()
                        }
                        composable(NavRoot.BADGE) {
                            Badge()
                        }
                        composable(NavRoot.BOTTOMNAV) {
                            BottomNavigation()
                        }
                        composable(NavRoot.BUTTON) {
                            Button()
                        }
                        composable(NavRoot.CATEGORY) {
                            Category()
                        }
                        composable(NavRoot.CHECKBOX) {
                            CheckBox()
                        }
                        composable(NavRoot.DIVIDER) {
                            Divider()
                        }
                        composable(NavRoot.LISTIEM) {
                            ListItem()
                        }
                        composable(NavRoot.MEMBERLIST) {
                            MemberList()
                        }
                        composable(NavRoot.OAUTHBUTTON) {
                            OAuthButton()
                        }
                        composable(NavRoot.TOGGLE) {
                            Toggle()
                        }
                        composable(NavRoot.SHADOW) {
                            Shadow()
                        }
                        composable(NavRoot.DROPDOWN) {
                            Dropdown()
                        }
                        composable(NavRoot.TOPBAR) {
                            TopBar()
                        }
                        composable(NavRoot.TOOLTIP) {
                            ToolTip()
                        }
                        composable(NavRoot.DIALOG) {
                            Dialog()
                        }
                    }
                }
            }
        }
    }
}
