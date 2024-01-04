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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotapp.mobile.ui.R

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
        Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
    }

    if (uiState.isSuccessfullySignUp) onGoToSignIn()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(onClick = onGoBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.width(250.dp),
            text = "Create new account",
            lineHeight = 40.sp,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = name,
            onValueChange = {
                viewModel.cleanError()
                name = it
            },
            label = {
                Text(text = "Full name")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            maxLines = 1,
        )

        TextField(
            value = email,
            onValueChange = {
                viewModel.cleanError()
                email = it
            },
            label = {
                Text(text = "Email address")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1
        )

        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = pass,
            onValueChange = {
                pass = it
            },
            label = {
                Text(text = "Create password")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.visibility_icon)
                else painterResource(id = R.drawable.visibility_icon_off)

                IconButton(onClick = {
                    viewModel.cleanError()
                    passwordVisible = !passwordVisible
                }) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            maxLines = 1,
        )



        Button(
            onClick = { viewModel.onSignUp(name, email, pass) },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 242, green = 58, blue = 68, alpha = 255),
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign Up!")
        }

    }
}