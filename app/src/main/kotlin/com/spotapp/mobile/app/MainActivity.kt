package com.spotapp.mobile.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.spotapp.mobile.app.navigation.Destinations
import com.spotapp.mobile.app.navigation.NavGraph
import com.spotapp.mobile.app.state.AppStateManager
import com.spotapp.mobile.app.theme.SpotTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appStateManager: AppStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val startDestination =
                Destinations.startDestination(appStateManager)

            setContent {
                SpotTheme {
                    NavGraph(
                        startDestination = startDestination,
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}
