package com.ahgitdevelopment.filmfamily.screens.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahgitdevelopment.filmfamily.R
import com.ahgitdevelopment.filmfamily.repository.UserRepository
import com.ahgitdevelopment.filmfamily.screens.login.LoginViewModel.AuthResultCode.CANCELLED
import com.ahgitdevelopment.filmfamily.screens.login.LoginViewModel.AuthResultCode.ERROR
import com.ahgitdevelopment.filmfamily.screens.login.LoginViewModel.AuthResultCode.NOT_APPLICABLE
import com.ahgitdevelopment.filmfamily.screens.login.LoginViewModel.AuthResultCode.NO_NETWORK
import com.ahgitdevelopment.filmfamily.screens.login.LoginViewModel.AuthResultCode.OK
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel(), FirebaseAuthManager {

    val isAnonymousUser: StateFlow<Boolean> = userRepository.isAnonymousUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true
    )

    //Used to perform appropriate action based on the login result
    private val _authResultCode = MutableStateFlow(NOT_APPLICABLE)
    val authResultCode = _authResultCode.asStateFlow()

    init {
        firebaseAuth.signOut()
    }

    override fun buildLoginIntent(): Intent {
//        val authUILayout = AuthMethodPickerLayout.Builder(R.layout.auth_ui)
//            .setGoogleButtonId(R.id.btn_gmail)
//            .setEmailButtonId(R.id.btn_email)
//            .build()

        return AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
//            .enableAnonymousUsersAutoUpgrade()
            .setLogo(R.mipmap.ic_launcher)
//            .setAuthMethodPickerLayout(authUILayout)
            .build()
    }

    override fun onLoginResult(result: FirebaseAuthUIAuthenticationResult) {

        Log.d(TAG, "onLoginResult triggered")
        Log.d(TAG, result.toString())


        val response: IdpResponse? = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {

            viewModelScope.launch {

                userRepository.setAnonymousUser(false)

                response?.user?.let { user ->
                    userRepository.setUserName(user.name)
                    userRepository.setUserEmail(user.email)
                }
            }

            _authResultCode.value = OK

            Log.d(TAG, "Login successful")
            return
        }


        val userPressedBackButton = (response == null)
        if (userPressedBackButton) {
            _authResultCode.value = CANCELLED
            Log.d(TAG, "Login cancelled by user")
            return
        }

        when (response?.error?.errorCode) {
            ErrorCodes.NO_NETWORK -> {
                _authResultCode.value = NO_NETWORK

                Log.d(TAG, "Login failed on network connectivity")
            }

            ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> {
                val nonAnonymousCredForLinking: AuthCredential = response.credentialForLinking!!

                viewModelScope.launch {
                    Log.d(TAG, "handleMergeConflict")
                }
            }

            else -> {
                Log.d(TAG, "Login failed")
                _authResultCode.value = ERROR
            }
        }
    }

    enum class AuthResultCode {
        NOT_APPLICABLE,
        OK, CANCELLED,
        MERGED,
        NO_NETWORK,
        ERROR,
        LOGGING_OUT,
        LOGGED_OUT
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}