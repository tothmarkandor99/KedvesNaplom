package com.webtic.kedvesnaplom.ui.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.webtic.kedvesnaplom.ui.about.AboutPage
import com.webtic.kedvesnaplom.ui.details.DetailsPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            AppNavigation(firebaseAnalytics)
        }
    }
}

@Composable
fun AppNavigation(
    firebaseAnalytics: FirebaseAnalytics,
) {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { _, destination, _ ->
        Log.d("KN", destination.route.toString())
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route as String?)
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.route as String?)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }
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
