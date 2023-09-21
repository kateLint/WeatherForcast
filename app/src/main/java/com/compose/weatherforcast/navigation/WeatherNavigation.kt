package com.compose.weatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.weatherforcast.screens.about.AboutScreen
import com.compose.weatherforcast.screens.favorites.FavoritesScreen
import com.compose.weatherforcast.screens.main.MainScreen
import com.compose.weatherforcast.screens.main.MainViewModel
import com.compose.weatherforcast.screens.search.SearchScreen
import com.compose.weatherforcast.screens.settings.SettingScreen
import com.compose.weatherforcast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController)
        }
        //www.google.com/cityname="seattle"
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                })){ navBack ->
            navBack.arguments?.getString("city").let { city ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel,
                    city = city)
            }


        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController)
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController)
        }
        composable(WeatherScreens.SettingsScreen.name){
            SettingScreen(navController)
        }
        composable(WeatherScreens.FavoriteScreen.name){
            FavoritesScreen(navController)
        }
    }
}