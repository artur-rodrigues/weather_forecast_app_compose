package com.example.weatherforecastapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecastapp.ui.screen.about.AboutScreen
import com.example.weatherforecastapp.ui.screen.favorite.FavoriteScreen
import com.example.weatherforecastapp.ui.screen.main.MainScreen
import com.example.weatherforecastapp.ui.screen.main.MainViewModel
import com.example.weatherforecastapp.ui.screen.search.SearchScreen
import com.example.weatherforecastapp.ui.screen.settings.SettingsScreen
import com.example.weatherforecastapp.ui.screen.splash.WeatherSplashScreen
import com.example.weatherforecastapp.util.Constants.ARGUMENT_KEY_CITY

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SPLASH.screen) {

        composable(WeatherScreens.SPLASH.screen) {
            WeatherSplashScreen(navController)
        }

        val route = WeatherScreens.MAIN.screen

        composable(
            route = "$route/{$ARGUMENT_KEY_CITY}",
            arguments = listOf(
                navArgument(ARGUMENT_KEY_CITY) {
                    type =  NavType.StringType
                }
            )
        ) {
            it.arguments?.getString(ARGUMENT_KEY_CITY)?.let { city ->
                MainScreen(city, navController, hiltViewModel<MainViewModel>())
            }
        }

        composable(WeatherScreens.SEARCH.screen) {
            SearchScreen(navController)
        }

        composable(WeatherScreens.ABOUT.screen) {
            AboutScreen(navController)
        }

        composable(WeatherScreens.FAVORITE.screen) {
            FavoriteScreen(navController)
        }

        composable(WeatherScreens.SETTINGS.screen) {
            SettingsScreen(navController)
        }
    }
}