package com.seugi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.main.navigation.MAIN_ROUTE
import com.seugi.main.navigation.mainScreen
import com.seugi.main.navigation.navigateToMain
import com.seugi.onboarding.navigation.ONBOARDING_ROUTE
import com.seugi.onboarding.navigation.onboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController: NavHostController = rememberNavController()
            SeugiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = ONBOARDING_ROUTE,
                    ) {
                        mainScreen(
                            mainToOnboarding = {
                                navHostController.popBackStack()
                            },
                        )
                        onboardingScreen(
                            onboardingToMain = {
                                while (navHostController.popBackStack()) {
                                }
                                navHostController.navigateToMain(
                                    toRoute = MAIN_ROUTE,
                                    fromRoute = ONBOARDING_ROUTE,
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}
