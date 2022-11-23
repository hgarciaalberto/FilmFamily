package com.ahgitdevelopment.filmfamily.screens.dashboard

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahgitdevelopment.filmfamily.navigation.BottomNavigation


@Composable
fun DashboardScreen(navController: NavController) {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        ) {

    }
}


@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

//        NavigationGraph(navController = navController)
    }
}