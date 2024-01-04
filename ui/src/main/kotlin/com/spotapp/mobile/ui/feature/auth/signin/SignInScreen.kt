package com.spotapp.mobile.ui.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotapp.mobile.ui.R

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    onGoBack: () -> Unit,
    onGoToHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    if (uiState.errorMessage != null) {
        Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
    }

    if (uiState.isSuccessfullySignIn) onGoToHome()

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
                contentDescription = null,
            )
        }

        Text(
            modifier = Modifier.width(200.dp),
            text = "Welcome Back!",
            lineHeight = 40.sp,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = email,
            onValueChange = {
                viewModel.cleanError()
                email = it
            },
            label = {
                Text(text = "Email Address")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )


        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = pass,
            onValueChange = {
                viewModel.cleanError()
                pass = it
            },
            label = {
                Text(text = "Password")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.visibility_icon)
                else painterResource(id = R.drawable.visibility_icon_off)

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        painter = image, contentDescription = null
                    )
                }
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Remember me")

            var switchState by remember {
                mutableStateOf(false)
            }

            Switch(checked = switchState, onCheckedChange = {
                viewModel.cleanError()
                switchState = it
            })
        }

        Button(
            onClick = { viewModel.onSignIn(email, pass) },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 242, green = 58, blue = 68, alpha = 255),
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign In", fontWeight = FontWeight.Bold)
        }

    }
}