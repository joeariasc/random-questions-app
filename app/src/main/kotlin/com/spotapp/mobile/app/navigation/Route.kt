package com.spotapp.mobile.app.navigation

import com.spotapp.mobile.domain.model.state.AppState
import com.spotapp.mobile.domain.usecases.GetCurrentAppState

typealias Route = String

object Destinations {

    fun home(): Route = "/home"

    fun auth(): Route = "/auth"

    suspend fun startDestination(getCurrentAppState: GetCurrentAppState): String {
        if (getCurrentAppState() == AppState.SIGNED_ON) {
            return home()
        }
        return auth()
    }
}
