package com.spotapp.mobile.ui.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        onAnonymousSignInClick = viewModel::onAnonymousSignInClick,
        onUsingNameAndEmailSignInClick = viewModel::onUsingNameAndEmailSignInClick,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onSignInWithNameAndEmailClick = viewModel::onSignInWithNameAndEmailClick,
    )
}

@Composable
private fun Auth(
    state: UiState,
    onAnonymousSignInClick: () -> Unit = {},
    onUsingNameAndEmailSignInClick: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onSignInWithNameAndEmailClick: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(modifier = Modifier.padding(vertical = 10.dp), text = "Sign-In to Spot")

        when (state) {
            is UiState.Anonymous -> AnonymousAuth()
            is UiState.Idle -> IdleAuth(onAnonymousSignInClick, onUsingNameAndEmailSignInClick)
            is UiState.WithNameAndEmail -> NameAndEmailAuth(
                state,
                onNameChange,
                onEmailChange,
                onSignInWithNameAndEmailClick,
            )

            is UiState.Loading -> LoadingAuth()
        }

    }
}

@Composable
private fun NameAndEmailAuth(
    uiState: UiState.WithNameAndEmail,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    OutlinedTextField(
        value = uiState.name ?: "",
        placeholder = {
            Text(text = "Full Name")
        },
        onValueChange = onNameChange,
    )
    OutlinedTextField(
        value = uiState.email ?: "",
        placeholder = {
            Text(text = "Email")
        },
        onValueChange = onEmailChange,
    )
    Button(onClick = onSignInClick) {
        Text(text = "Sign-In Now")
    }
}

@Composable
private fun AnonymousAuth() {
    Text(text = "Signed-In Anonymously")
}


@Composable
private fun LoadingAuth() {
    CircularProgressIndicator()
}

@Composable
private fun IdleAuth(
    onAnonymousSignInClick: () -> Unit,
    onUsingNameAndEmailSignInClick: () -> Unit,
) {
    Button(onClick = onAnonymousSignInClick) {
        Text(text = "Anonymously")
    }
    Button(onClick = onUsingNameAndEmailSignInClick) {
        Text(text = "Using name and email")
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
        state = UiState.WithNameAndEmail(
            email = null,
            name = null,
        )
    )
}
