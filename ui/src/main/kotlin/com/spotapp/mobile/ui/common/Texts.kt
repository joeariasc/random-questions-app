package com.spotapp.mobile.ui.common

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeadLineTitle(
    text: String,
) {
    Text(
        modifier = Modifier.width(200.dp),
        text = text,
        lineHeight = 40.sp,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    )
}