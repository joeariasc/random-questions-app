package com.spotapp.mobile.data.mapper

import android.database.sqlite.SQLiteException
import com.spotapp.mobile.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> withLocalSource(crossinline query: suspend () -> T): Result<T> =
    withContext(Dispatchers.IO) {
        try {
            Result.Success(query())
        } catch (e: SQLiteException) {
            Result.Error(e)
        }
    }
