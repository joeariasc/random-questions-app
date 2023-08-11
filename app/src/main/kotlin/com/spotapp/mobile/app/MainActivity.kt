package com.spotapp.mobile.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.spotapp.mobile.app.navigation.Destinations
import com.spotapp.mobile.app.navigation.NavGraph
import com.spotapp.mobile.app.theme.SpotTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var application: SpotApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = applicationContext as SpotApplication

        lifecycleScope.launch {

            val startDestination =
                Destinations.startDestination(application.appStateManager)

            setContent {
                SpotTheme {
                    NavGraph(
                        startDestination = startDestination,
                        navController = rememberNavController(),
                        appStateManager = application.appStateManager,
                    )
                }
            }
        }

    }

}
