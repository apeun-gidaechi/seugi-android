package com.apeun.gidaechi

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.login.navigation.emailSignInScreen
import com.apeun.gidaechi.login.navigation.navigateToEmailSignIn
import com.apeun.gidaechi.navigation.START_ROUTE
import com.apeun.gidaechi.navigation.emailSignUpScreen
import com.apeun.gidaechi.navigation.emailVerificationScreen
import com.apeun.gidaechi.navigation.joinSuccess
import com.apeun.gidaechi.navigation.navigateToEmailSignUp
import com.apeun.gidaechi.navigation.navigateToEmailVerification
import com.apeun.gidaechi.navigation.navigateToJoinSuccess
import com.apeun.gidaechi.navigation.navigateToOAuthSignUp
import com.apeun.gidaechi.navigation.navigateToSchoolCode
import com.apeun.gidaechi.navigation.navigateToSelectingJob
import com.apeun.gidaechi.navigation.navigateToStart
import com.apeun.gidaechi.navigation.navigateToWaitingJoin
import com.apeun.gidaechi.navigation.oauthSignUp
import com.apeun.gidaechi.navigation.schoolCode
import com.apeun.gidaechi.navigation.selectingJob
import com.apeun.gidaechi.navigation.startScreen
import com.apeun.gidaechi.navigation.waitingJoin

private const val NAVIGATION_ANIM = 400

@Composable
internal fun OnboardingScreen(navHostController: NavHostController = rememberNavController(), onboardingToMain: () -> Unit) {
    NavHost(
        navController = navHostController,
        startDestination = START_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
        popEnterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        popExitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
    ) {
        startScreen(
            navigateToEmailSignIn = { navHostController.navigateToEmailSignIn() },
            navigateToOAuthSignIn = { navHostController.navigateToOAuthSignUp() },
        )
        emailSignInScreen(
            onboardingToMain = onboardingToMain,
            navigateToOAuthSignUp = { navHostController.navigateToEmailSignUp() },
            popBackStack = { navHostController.navigateToStart() },
        )
        emailSignUpScreen(
            navigateToEmailVerification = { name, email, password ->
                navHostController.navigateToEmailVerification(
                    name = name,
                    email = email,
                    password = password,
                )
            },
            popBackStack = { navHostController.navigateToStart() },
        )
        emailVerificationScreen(
            navigateToSchoolCode = { navHostController.navigateToSchoolCode() },
            popBackStack = { navHostController.navigateToStart() },
        )
        schoolCode(
            navigateToJoinSuccess = { schoolCode, workspaceId, workspaceName, workspaceImageUrl, studentCount, teacherCount ->
                navHostController.navigateToJoinSuccess(
                    schoolCode = schoolCode,
                    workspaceId = workspaceId,
                    workspaceName = workspaceName,
                    workspaceImageUrl = workspaceImageUrl,
                    studentCount = studentCount,
                    teacherCount = teacherCount,
                )
            },
            popBackStack = { navHostController.popBackStack() },
        )
        joinSuccess(
            navigateToSelectingJob = { workspaceId, schoolCode ->
                navHostController.navigateToSelectingJob(
                    workspaceId = workspaceId,
                    schoolCode = schoolCode,
                )
            },
            popBackStack = { navHostController.popBackStack() },
        )
        selectingJob(
            navigateToWaitingJoin = { navHostController.navigateToWaitingJoin() },
            popBackStack = { navHostController.popBackStack() },
        )
        waitingJoin(
            popBackStack = { navHostController.popBackStack() },
        )

        oauthSignUp(
            popBackStack = { navHostController.popBackStack() },
            navigateToEmailSignUp = { navHostController.navigateToEmailSignUp() },
        )
    }
}
