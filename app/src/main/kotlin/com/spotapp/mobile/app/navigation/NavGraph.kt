package com.spotapp.mobile.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spotapp.mobile.app.di.AppModule
import com.spotapp.mobile.ui.ext.createViewModel
import com.spotapp.mobile.ui.feature.auth.AuthScreen
import com.spotapp.mobile.ui.feature.auth.AuthViewModel
import com.spotapp.mobile.ui.feature.home.HomeScreen

@Composable
fun NavGraph(
    appModule: AppModule,
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.home()) {
            HomeScreen(
                viewModel = viewModel()
            )
        }

        composable(route = Destinations.auth()) {
            AuthScreen(
                viewModel = createViewModel {
                    AuthViewModel(
                        registerUserFirebase = appModule.domain.authenticateUserFirebase,
                        signInUserFirebase = appModule.domain.signInUserFirebase
                    )
                },
                navigateToHome = {
                    navController.navigate(Destinations.home()) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
