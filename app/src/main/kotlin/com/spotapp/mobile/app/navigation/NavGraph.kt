package com.spotapp.mobile.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spotapp.mobile.app.di.AppModule
import com.spotapp.mobile.ui.ext.createViewModel
import com.spotapp.mobile.ui.feature.auth.signin.SignInScreen
import com.spotapp.mobile.ui.feature.auth.signin.SignInViewModel
import com.spotapp.mobile.ui.feature.auth.signup.SignUpScreen
import com.spotapp.mobile.ui.feature.auth.signup.SignUpViewModel
import com.spotapp.mobile.ui.feature.home.HomeScreen
import com.spotapp.mobile.ui.feature.home.HomeViewModel
import com.spotapp.mobile.ui.feature.welcome.WelcomeScreen

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

        composable(route = Destinations.welcome()) {
            WelcomeScreen(
                onGoToSignIn = { navController.navigate(route = Destinations.signIn()) },
                onGoToSignUp = { navController.navigate(route = Destinations.signUp()) }
            )
        }

        composable(route = Destinations.signIn()) {
            SignInScreen(
                viewModel = createViewModel {
                    SignInViewModel(
                        usersRepository = appModule.data.usersRepository
                    )
                },
                onGoBack = {
                    navController.navigate(Destinations.welcome()) {
                        popUpTo(0)
                    }
                },
                onGoToHome = {
                    navController.navigate(Destinations.home())
                }
            )
        }

        composable(route = Destinations.signUp()) {
            SignUpScreen(
                viewModel = createViewModel {
                    SignUpViewModel(usersRepository = appModule.data.usersRepository)
                },
                onGoBack = {
                    navController.navigate(Destinations.welcome()) {
                        popUpTo(0)
                    }
                },
                onGoToSignIn = { navController.navigate(route = Destinations.signIn()) }
            )
        }

        composable(route = Destinations.home()) {
            HomeScreen(
                viewModel = createViewModel {
                    HomeViewModel(usersRepository = appModule.data.usersRepository)
                }
            )
        }
    }
}
