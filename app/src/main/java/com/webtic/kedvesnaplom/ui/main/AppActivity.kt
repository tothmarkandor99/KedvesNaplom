package com.webtic.kedvesnaplom.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.webtic.kedvesnaplom.ui.about.AboutPage
import com.webtic.kedvesnaplom.ui.details.DetailsPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route
    ) {
        composable(NavScreen.Home.route) {
            MainPage(
                viewModel = hiltViewModel(),
                navController,
            )
        }
        composable(NavScreen.About.route) {
            AboutPage()
        }
        composable(
            route = NavScreen.BejegyzesDetails.route,
        ) {
            DetailsPage(viewModel = hiltViewModel(), navController, null)
        }
        composable(
            route = NavScreen.BejegyzesDetails.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.BejegyzesDetails.argument0) {
                defaultValue = "-1"
            })
        ) { backStackEntry ->
            val azonosito =
                backStackEntry.arguments?.getString(NavScreen.BejegyzesDetails.argument0)
            DetailsPage(viewModel = hiltViewModel(), navController, azonosito?.toInt())
        }
    }
}

sealed class NavScreen(val route: String) {
    object Home : NavScreen("Home")
    object About : NavScreen("About")
    object BejegyzesDetails : NavScreen("BejegyzesDetails") {
        const val routeWithArgument: String = "BejegyzesDetails/{azonosito}"
        const val argument0: String = "azonosito"
    }
}
