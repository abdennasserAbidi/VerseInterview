package com.example.stadiumseeker.feature.navigation

sealed class Screen(val route:String) {
    object SignUpScreen :Screen("signup_screen")
    object SignInScreen :Screen("signin_screen")
    object DetailScreen :Screen("detail_screen")
    object HomeScreen :Screen("home_screen")
    object FilterScreen :Screen("filter_screen")
    object ChooseTopicScreen :Screen("choose_topic_screen")
    object TestScreen :Screen("test_screen")
    object HistoryScreen :Screen("history_screen")
    object RateScreen :Screen("rate_screen")
    object TournamentScreen :Screen("tournament_screen")
}