package com.example.verseinterview.navigation

sealed class Screen(val route:String) {
    object VideoScreen : Screen("video_screen")
    object InfoScreen : Screen("info_screen")
}