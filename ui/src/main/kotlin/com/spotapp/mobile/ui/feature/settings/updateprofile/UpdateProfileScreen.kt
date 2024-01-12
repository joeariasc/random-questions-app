package com.spotapp.mobile.ui.feature.settings.updateprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.spotapp.mobile.ui.R
import com.spotapp.mobile.ui.common.StandardAlertDialog

@Composable
fun UpdateProfileScreen(
    viewModel: UpdateProfileViewModel,
    paddingValues: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf(uiState.userName) }

    if (uiState.errorMessage != null) {
        StandardAlertDialog(
            onDismissRequest = viewModel::cleanError,
            dialogText = uiState.errorMessage!!
        )
    }

    if (uiState.showConfirmationModal) {
        StandardAlertDialog(
            onDismissRequest = viewModel::hideConfirmationModal,
            dialogTitle = stringResource(id = R.string.done),
            dialogText = stringResource(id = R.string.successful_update),
            icon = Icons.Default.Check
        )
    }

    if (uiState.showEditPasswordDialog) {
        UpdateEmailDialog(
            onDismissRequest = viewModel::hideUpdatePasswordDialog,
            onConfirmation = viewModel::updateUserPassword,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name ?: "",
                    onValueChange = { name = it },
                    label = {
                        Text(text = stringResource(id = R.string.name))
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        viewModel.updateUserName(name ?: "")
                    }
                ) {
                    Text(text = stringResource(id = R.string.update_user_name))
                }
            }
            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.mainColor),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = viewModel::showUpdatePasswordDialog
                ) {
                    Text(text = stringResource(id = R.string.update_user_password))
                }
            }

        }
    }
}

@Composable
fun UpdateEmailDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit,
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(320.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(Icons.Default.Email, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.update_email_summary),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = {
                        Text(text = stringResource(id = R.string.current_password))
                    },
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

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = {
                        Text(text = stringResource(id = R.string.new_password))
                    },
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                    ) {
                        Text(stringResource(id = R.string.dismiss))
                    }
                    TextButton(
                        onClick = { onConfirmation(currentPassword, newPassword) },
                    ) {
                        Text(stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}