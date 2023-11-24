package com.spotapp.mobile.ui.feature.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(viewModel: AuthViewModel, navigateToHome: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isAuthenticated) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }

    Auth(
        state = uiState,
        onRegisterClick = viewModel::registerNewUserClick,
        onLoginClick = viewModel::logInClick,
        onPasswordChange = viewModel::onPasswordChange,
        onEmailChange = viewModel::onEmailChange,
        onRegisterUser = viewModel::onRegisterUser,
        onLoginUserClick = viewModel::onLogInUser
    )
}

@Composable
private fun Auth(
    state: UiState,
    onRegisterClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onRegisterUser: () -> Unit = {},
    onLoginUserClick: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(vertical = 10.dp), text = "Welcome to Spot")

        when (state) {
            is UiState.Idle -> IdleAuth(
                onRegisterClick,
                onLoginClick,
            )

            is UiState.Register -> RegisterUser(
                state = state,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onButtonAction = onRegisterUser
            )

            is UiState.Login -> SignInUser(
                state = state,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onButtonAction = onLoginUserClick
            )

            is UiState.Loading -> LoadingAuth()
        }
    }
}

@Composable
private fun LoadingAuth() {
    CircularProgressIndicator()
}

@Composable
private fun IdleAuth(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onRegisterClick) {
            Text(text = "Register on the App")
        }
        Button(onClick = onLoginClick) {
            Text(text = "Are you already registered? Log In")
        }
    }
}

@Composable
private fun RegisterUser(
    state: UiState.Register,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onButtonAction: () -> Unit,
) {
    AuthenticationContent(
        email = state.email,
        password = state.password,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        errorMessage = state.errorMessage,
        buttonText = "Register User",
        onButtonAction = onButtonAction,
    )
}

@Composable
private fun SignInUser(
    state: UiState.Login,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onButtonAction: () -> Unit,
) {
    AuthenticationContent(
        email = state.email,
        password = state.password,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        errorMessage = state.errorMessage,
        buttonText = "Sign In",
        onButtonAction = onButtonAction,
    )
}

@Composable
private fun AuthenticationContent(
    email: String?,
    password: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorMessage: String? = null,
    buttonText: String,
    onButtonAction: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = email ?: "",
            placeholder = {
                Text(text = "Email")
            },
            onValueChange = onEmailChange,
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = password ?: "",
            placeholder = {
                Text(text = "Password")
            },
            onValueChange = onPasswordChange,
            isError = errorMessage != null,
        )
        if (errorMessage != null) {
            Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
        }
        Button(onClick = onButtonAction) {
            Text(text = buttonText, textAlign = TextAlign.Center)
        }
    }
}

@Preview(widthDp = 200, heightDp = 400)
@Composable
fun AuthIdlePreview() {
    Auth(
        state = UiState.Idle()
    )
}

@Preview(widthDp = 200, heightDp = 400)
@Composable
fun AuthNameAndEmailPreview() {
    Auth(
        state = UiState.Register(
            email = null,
            password = null
        )
    )
}
