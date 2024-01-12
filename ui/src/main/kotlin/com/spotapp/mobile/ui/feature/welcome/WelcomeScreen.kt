package com.spotapp.mobile.ui.feature.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotapp.mobile.ui.R
import com.spotapp.mobile.ui.common.SingleButton
import com.spotapp.mobile.ui.common.StandardAlertDialog

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    onGoToHome: () -> Unit,
    onGoToSignUp: () -> Unit,
    onGoToSignIn: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.errorMessage != null) {
        StandardAlertDialog(
            onDismissRequest = viewModel::cleanError,
            dialogText = uiState.errorMessage!!
        )
    }

    if (uiState.isSuccessfullySignIn) onGoToHome()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "")
                Text(
                    text = stringResource(id = R.string.welcome),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(id = R.string.welcome_summary),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(.3F)
                )
            }


            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_red_black),
                    contentDescription = null
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SingleButton(
                    buttonText = stringResource(id = R.string.go_to_sign_in),
                    onClick = onGoToSignIn
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onGoToSignUp,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.go_to_sign_up),
                        color = colorResource(id = R.color.mainColor),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                SingleButton(
                    onClick = viewModel::onSignIn,
                    buttonText = stringResource(id = R.string.sign_in_anonymously),
                    containerColor = R.color.MidnightBlue
                )
            }
        }
    }
}