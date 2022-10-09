package com.ahgitdevelopment.filmfamily.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("SplashScreen")
    object LoginScreen : AppScreens("LoginScreen")
}
