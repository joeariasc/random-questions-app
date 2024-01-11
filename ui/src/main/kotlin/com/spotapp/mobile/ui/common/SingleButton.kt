package com.spotapp.mobile.ui.common

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.spotapp.mobile.ui.R

@Composable
fun SingleButton(
    onClick: () -> Unit = {},
    buttonText: String,
    @ColorRes containerColor: Int = R.color.mainColor,
) {
    Button(
        onClick = onClick,
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = containerColor),
            contentColor = Color.White
        )
    ) {
        Text(text = buttonText, fontWeight = FontWeight.Bold)
    }
}