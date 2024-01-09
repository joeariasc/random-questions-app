package com.spotapp.mobile.ui.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.spotapp.mobile.ui.R
import com.spotapp.mobile.ui.common.StandardAlertDialog

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onGoToWelcome: () -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoggedOut) onGoToWelcome()

    if (uiState.errorMessage != null) {
        StandardAlertDialog(
            onDismissRequest = viewModel::cleanError,
            dialogText = uiState.errorMessage!!
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePic()
        Card(
            modifier = Modifier.padding(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFBFBFBF)
            ),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Color.Black),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = uiState.userDto?.userInfo?.name.let {
                            if (it.isNullOrEmpty()) "No Name" else it
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Software Engineer")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = uiState.userDto?.userInfo?.email ?: "No Email")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExtendedFloatingActionButton(
            onClick = viewModel::signOut,
            icon = { Icon(Icons.Filled.Edit, null) },
            text = { Text(text = stringResource(id = R.string.sign_out)) },
        )
    }
}

@Composable
private fun ProfilePic() {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    Image(
        painter = painterResource(id = R.drawable.profile_picture),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .border(
                BorderStroke(4.dp, rainbowColorsBrush),
                CircleShape
            )
            .padding(4.dp)
            .clip(CircleShape)
    )
}