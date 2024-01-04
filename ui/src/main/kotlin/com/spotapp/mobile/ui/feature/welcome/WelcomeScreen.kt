package com.spotapp.mobile.ui.feature.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotapp.mobile.ui.R

@Composable
fun WelcomeScreen(
    onGoToSignUp: () -> Unit,
    onGoToSignIn: () -> Unit,
) {
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
                    text = "Welcome",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sign In or create a new account",
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
                Button(
                    onClick = onGoToSignIn,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .absolutePadding(bottom = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 242, green = 58, blue = 68, alpha = 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Go to Sign In")
                }

                OutlinedButton(
                    onClick = onGoToSignUp,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .absolutePadding(top = 5.dp)
                ) {
                    Text(
                        text = "No account yet? Register here",
                        color = Color(red = 242, green = 58, blue = 68, alpha = 255)
                    )

                }
            }
        }
    }
}

@Preview(widthDp = 400, heightDp = 400, showBackground = false)
@Composable
fun WelcomePreview() {
    WelcomeScreen({}, {})
}