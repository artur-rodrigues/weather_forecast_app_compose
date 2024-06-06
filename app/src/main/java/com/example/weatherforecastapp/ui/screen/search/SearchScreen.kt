package com.example.weatherforecastapp.ui.screen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.ui.component.DefaultScaffold
import com.example.weatherforecastapp.ui.component.SearchBar
import com.example.weatherforecastapp.ui.navigation.WeatherScreens
import com.example.weatherforecastapp.util.PassOnAction

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchScreen(
    navController: NavController = rememberNavController()
) {
    DefaultScaffold(
        title = "Search",
        navController = navController,
    ) {
        SearchContent {
            navController.navigate("${WeatherScreens.MAIN.screen}/$it") {
                popUpTo(WeatherScreens.SEARCH.screen) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun SearchContent(
    action: PassOnAction<String>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSearch = action
        )
    }
}