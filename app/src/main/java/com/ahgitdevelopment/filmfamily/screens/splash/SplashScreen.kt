package com.ahgitdevelopment.filmfamily.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahgitdevelopment.filmfamily.R
import com.ahgitdevelopment.filmfamily.navigation.AppScreens
import com.ahgitdevelopment.filmfamily.ui.theme.FilmFamilyTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {

    val finish by viewModel.finish.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = finish) {
        if (finish) {
            navController.popBackStack()
            navController.navigate(AppScreens.LoginScreen.route)
        }
    }

    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.androidzen_logo),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Blue),
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AnimationSplashPreview() {
    FilmFamilyTheme {
        Splash()
    }
}
