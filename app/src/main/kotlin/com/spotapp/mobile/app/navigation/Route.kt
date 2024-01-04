package com.spotapp.mobile.app.navigation

import com.spotapp.mobile.domain.usecases.GetCurrentAppState

typealias Route = String

object Destinations {

    fun welcome(): Route = "/welcome"

    fun signIn(): Route = "/sign-in"

    fun signUp(): Route = "/signUp"

    fun home(): Route = "/home"

    fun auth(): Route = "/auth"

    suspend fun startDestination(getCurrentAppState: GetCurrentAppState): String =
        if (getCurrentAppState()) home() else welcome()
}
