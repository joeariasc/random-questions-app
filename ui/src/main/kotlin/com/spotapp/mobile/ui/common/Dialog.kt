package com.spotapp.mobile.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.spotapp.mobile.ui.R


@Composable
fun StandardAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (() -> Unit)? = null,
    dialogTitle: String = stringResource(id = R.string.wait),
    dialogText: String = stringResource(id = R.string.generic_error_message),
    icon: ImageVector = Icons.Default.Info,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = null)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            onConfirmation?.let {
                TextButton(
                    onClick = {
                        it()
                    }
                ) {
                    Text(stringResource(id = R.string.confirm))
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
fun StandardAlertDialogPreview() {
    StandardAlertDialog(
        onDismissRequest = {},
        onConfirmation = {},
    )
}