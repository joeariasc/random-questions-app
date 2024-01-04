package com.spotapp.mobile.data

data class DataResult<out T>(
    val data: T? = null,
    val error: String? = null
)