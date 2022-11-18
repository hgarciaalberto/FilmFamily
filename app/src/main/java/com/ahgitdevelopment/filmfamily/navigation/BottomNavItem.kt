package com.ahgitdevelopment.filmfamily.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {

    object Home : BottomNavItem("Home", Icons.Default.Home, AppScreens.LoginScreen.route)
    object Profile : BottomNavItem("Profile", Icons.Default.Person, AppScreens.ProfileScreen.route)

}