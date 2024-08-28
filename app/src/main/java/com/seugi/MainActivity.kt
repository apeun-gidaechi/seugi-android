package com.seugi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.loadTokenIsSave()
        setContent {
            val navHostController: NavHostController = rememberNavController()
            val state by mainViewModel.state.collectAsState()
            SeugiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    if (state == null) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(108.dp),
                                painter = painterResource(id = R.drawable.ic_seugi),
                                contentDescription = "앱 아이콘",
                            )
                        }
                    } else {
                        NavHost(
                            navController = navHostController,
                            startDestination = if (state == true) MAIN_ROUTE else ONBOARDING_ROUTE,
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
}
