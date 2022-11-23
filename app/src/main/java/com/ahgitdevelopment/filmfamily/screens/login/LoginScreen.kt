package com.ahgitdevelopment.filmfamily.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahgitdevelopment.filmfamily.ui.theme.FilmFamilyTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val isAnonymousUser by viewModel.isAnonymousUser.collectAsStateWithLifecycle(lifecycleOwner)
    val authResultCode by viewModel.authResultCode.collectAsState()

    val loginLauncher = rememberLauncherForActivityResult(
        viewModel.buildLoginActivityResult()
    ) { result ->
        if (result != null) {
            viewModel.onLoginResult(result = result)
        }
    }

    if (isAnonymousUser && authResultCode != LoginViewModel.AuthResultCode.CANCELLED) {
        LaunchedEffect(true) {
            loginLauncher.launch(viewModel.buildLoginIntent())
        }
    }

    LoginScreenContent(isAnonymousUser, authResultCode)
}

@Composable
fun LoginScreenContent(isAnonymousUser: Boolean, authResultCode: LoginViewModel.AuthResultCode) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Is user Anonymous: $isAnonymousUser"
        )
        Text(
            text = "Result: " + when (authResultCode) {
                LoginViewModel.AuthResultCode.NOT_APPLICABLE -> "NOT_APPLICABLE"
                LoginViewModel.AuthResultCode.OK -> "OK"
                LoginViewModel.AuthResultCode.CANCELLED -> "CANCELLED"
                LoginViewModel.AuthResultCode.MERGED -> "MERGED"
                LoginViewModel.AuthResultCode.NO_NETWORK -> "NO_NETWORK"
                LoginViewModel.AuthResultCode.ERROR -> "ERROR"
                LoginViewModel.AuthResultCode.LOGGING_OUT -> "LOGGING_OUT"
                LoginViewModel.AuthResultCode.LOGGED_OUT -> "LOGGED_OUT"
            }
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    FilmFamilyTheme {
        LoginScreenContent(false, LoginViewModel.AuthResultCode.OK)
    }
}

// https://premsuman.medium.com/how-i-resolved-the-you-need-to-use-a-theme-appcompat-3ad8e24544e8
