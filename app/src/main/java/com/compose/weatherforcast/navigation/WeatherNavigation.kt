package com.compose.weatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.weatherforcast.screens.main.MainScreen
import com.compose.weatherforcast.screens.main.MainViewModel
import com.compose.weatherforcast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController)
        }
        composable(WeatherScreens.MainScreen.name){
            val mainViewMmodel = hiltViewModel<MainViewModel>()
            MainScreen(navController, mainViewMmodel)
        }

    }
}