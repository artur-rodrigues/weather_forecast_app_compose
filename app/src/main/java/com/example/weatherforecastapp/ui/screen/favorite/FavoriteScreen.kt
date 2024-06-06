package com.example.weatherforecastapp.ui.screen.favorite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.ui.component.CityRow
import com.example.weatherforecastapp.ui.component.DefaultScaffold
import com.example.weatherforecastapp.ui.component.ErrorMessage
import com.example.weatherforecastapp.ui.component.LoadingProgress
import com.example.weatherforecastapp.ui.navigation.WeatherScreens
import com.example.weatherforecastapp.util.PassOnAction

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavoriteScreen(
    navController: NavController = rememberNavController(),
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val ret = viewModel.favResult.collectAsState().value

    ret.run {
        when(this) {
            is Result.Success -> MainContent(
                favorites = data,
                navController = navController
            ) {
                viewModel.removeFavorite(it)
            }
            Result.Loading -> LoadingProgress()
            is Result.Error -> ErrorMessage(apiErrorResponse?.message ?: exception.message)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MainContent(
    favorites: List<Favorite>,
    navController: NavController,
    deleteAction: PassOnAction<Favorite>
) {
    val clickAction: PassOnAction<String> = {
        navController.navigate("${WeatherScreens.MAIN.screen}/$it") {
            popUpTo(WeatherScreens.FAVORITE.screen) {
                inclusive = true
            }
        }
    }

    DefaultScaffold(
        title = "Favorite Cities",
        navController = navController,
        padding = PaddingValues(5.dp)
    ) {
        FavoriteList(
            favorites = favorites,
            deleteAction = deleteAction,
            clickAction = clickAction
        )
    }
}

@Composable
fun FavoriteList(
    favorites: List<Favorite>,
    deleteAction: PassOnAction<Favorite>,
    clickAction: PassOnAction<String>
) {
    LazyColumn {
        items(favorites) {
            CityRow(favorite = it, deleteAction = deleteAction, clickAction)
        }
    }
}