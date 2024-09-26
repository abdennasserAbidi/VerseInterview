package com.example.verseinterview.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.verseinterview.screen.InfoScreen
import com.example.verseinterview.screen.VideoScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.VideoScreen.route) {
        composable(route = Screen.VideoScreen.route) {
            VideoScreen(navController = navController)
        }

        composable(route = Screen.InfoScreen.route) {
            InfoScreen(navController = navController)
        }
    }
}
