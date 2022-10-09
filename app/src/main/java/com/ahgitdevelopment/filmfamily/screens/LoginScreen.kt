package com.ahgitdevelopment.filmfamily.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahgitdevelopment.filmfamily.ui.theme.FilmFamilyTheme

@Composable
fun LoginScreen(navController: NavController) {
    Text(text = "hola")
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    FilmFamilyTheme {
        LoginScreen(rememberNavController())
    }
}
