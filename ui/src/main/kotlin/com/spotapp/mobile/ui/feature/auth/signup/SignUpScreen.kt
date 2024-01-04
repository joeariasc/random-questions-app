package com.spotapp.mobile.ui.feature.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotapp.mobile.ui.R
import com.spotapp.mobile.ui.common.HeadLineTitle
import com.spotapp.mobile.ui.common.InputPasswordField
import com.spotapp.mobile.ui.common.InputStringField
import com.spotapp.mobile.ui.common.SingleButton
import com.spotapp.mobile.ui.common.StandardAlertDialog
import com.spotapp.mobile.ui.feature.auth.AuthLayout

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onGoBack: () -> Unit,
    onGoToSignIn: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    if (uiState.errorMessage != null) {
        StandardAlertDialog(
            onDismissRequest = viewModel::cleanError,
            dialogText = uiState.errorMessage!!
        )
    }

    if (uiState.isSuccessfullySignUp) onGoToSignIn()

    AuthLayout {
        IconButton(onClick = onGoBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }

        HeadLineTitle(text = stringResource(id = R.string.new_account))

        InputStringField(
            value = name,
            onValueChange = { name = it },
            label = stringResource(id = R.string.new_name_label)
        )

        InputStringField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.email_address_label),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        InputPasswordField(value = pass, onValueChange = { pass = it })

        SingleButton(
            onClick = { viewModel.onSignUp(name, email, pass) },
            buttonText = stringResource(id = R.string.sign_up)
        )

    }
}