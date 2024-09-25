package com.example.stadiumseeker.feature.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stadiumseeker.feature.filter.FilterScreen
import com.example.stadiumseeker.feature.home.HomeScreen
import com.example.stadiumseeker.feature.login.TestScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.TestScreen.route) {
            TestScreen(navController = navController)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController) {

            }
        }

        composable(route = Screen.FilterScreen.route) {
            FilterScreen(onSubmit = {

            }, onBack = {})
        }
    }
}
