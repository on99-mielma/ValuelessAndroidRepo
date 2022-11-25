package com.on99.jet7thlpp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.on99.jet7thlpp.AnimatedSplashScreen
import com.on99.jet7thlpp.Myapp1

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route){
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route){
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
//                    Greeting("Android")
                Myapp1(modifier = Modifier.fillMaxSize())
            }
        }
    }
}