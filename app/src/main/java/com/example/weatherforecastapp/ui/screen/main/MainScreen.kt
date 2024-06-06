package com.example.weatherforecastapp.ui.screen.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Favorite
import com.example.weatherforecastapp.domain.model.MainWeather
import com.example.weatherforecastapp.domain.model.MainWeatherItem
import com.example.weatherforecastapp.domain.model.UnitType
import com.example.weatherforecastapp.ui.component.ErrorMessage
import com.example.weatherforecastapp.ui.component.LoadingProgress
import com.example.weatherforecastapp.ui.component.MaxMinTemp
import com.example.weatherforecastapp.ui.component.WeatherInfoRow
import com.example.weatherforecastapp.ui.component.WeatherStateImage
import com.example.weatherforecastapp.ui.component.WeatherStatusRow
import com.example.weatherforecastapp.ui.component.WeatherTagText
import com.example.weatherforecastapp.ui.navigation.WeatherScreens
import com.example.weatherforecastapp.ui.theme.WeatherBackground
import com.example.weatherforecastapp.ui.theme.YellowSun
import com.example.weatherforecastapp.ui.widgets.WeatherAppBar
import com.example.weatherforecastapp.util.Constants.WIND_SPEED_IMPERIAL
import com.example.weatherforecastapp.util.Constants.WIND_SPEED_METRIC
import com.example.weatherforecastapp.util.OnAction
import com.example.weatherforecastapp.util.getDummyMainWeather
import com.example.weatherforecastapp.util.toMainCondition
import com.example.weatherforecastapp.util.toMainDate
import com.example.weatherforecastapp.util.toMainIconUrl
import com.example.weatherforecastapp.util.toMainItemIconUrl
import com.example.weatherforecastapp.util.toMainItemWeatherDescription
import com.example.weatherforecastapp.util.toMainItemWeatherMaxTemperature
import com.example.weatherforecastapp.util.toMainItemWeatherMinTemperature
import com.example.weatherforecastapp.util.toMainTempDay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    city: String,
    navController: NavController,
    viewModel: MainViewModel
) {

    val isAlreadyFavList = when(val favCityResult = viewModel.favResult.collectAsState().value) {
        is Result.Success -> {
            favCityResult.data.any {
                city == it.city
            }
        }
        else -> false
    }

    val weatherData = produceState<Result<MainWeather>>(initialValue = Result.Loading) {
        value = viewModel.getWeather(city)
    }.value

    val unitTypeState = remember {
        viewModel.unitType
    }

    val context = LocalContext.current

    weatherData.run {
        when(this) {
            is Result.Success -> MainScaffold(
                mainWeather = data,
                unitType = unitTypeState.value,
                navController = navController,
                isAlreadyFavList = isAlreadyFavList
            ) {
                viewModel.insertFavorite(Favorite(data.city.name, data.city.country))
                Toast.makeText(context, "Add to Favorites", Toast.LENGTH_SHORT).show()
            }

            is Result.Error -> ErrorMessage(apiErrorResponse?.message ?: exception.message)

            Result.Loading -> LoadingProgress()
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun MainScaffold(
    mainWeather: MainWeather = getDummyMainWeather(),
    unitType: UnitType? = UnitType.getDefaultUnitType(),
    navController: NavController = rememberNavController(),
    isAlreadyFavList: Boolean = false,
    action: OnAction = {}
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 8.dp),
        topBar = {
            WeatherAppBar(
                title = "${mainWeather.city.name}, ${mainWeather.city.country}",
                icon = if(isAlreadyFavList) null else Icons.Default.Favorite,
                iconTint = Color.Red,
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SEARCH.screen)
                },
                onButtonClicked = action
            )
        }
    ) {
        MainContent(mainWeather = mainWeather, unitType = unitType, paddingValues = it)
    }
}

@Composable
fun MainContent(mainWeather: MainWeather, unitType: UnitType?, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Text(
            text = mainWeather.toMainDate(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        MainCentralInfo(mainWeather)

        Spacer(modifier = Modifier.height(5.dp))

        mainWeather.list[0].run {
            HumidityWindPressureRow(weatherItem = this, unitType = unitType)

            Divider()

            SunRiseAndSetRow(weatherItem = this)
        }

        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        WeatherOfWeek(mainWeather)
    }
}

@Composable
fun MainCentralInfo(mainWeather: MainWeather) {
    Surface (
        modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
        shape = CircleShape,
        color = YellowSun
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            WeatherStateImage(imageUrl = mainWeather.toMainIconUrl())

            Text(
                text = "${mainWeather.toMainTempDay()}°",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = mainWeather.toMainCondition(),
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weatherItem: MainWeatherItem, unitType: UnitType?) {
    WeatherInfoRow {

        WeatherStatusRow(
            id = R.drawable.humidity,
            iconDescription = "Humidity Icon",
            text = "${weatherItem.humidity}%"
        )

        WeatherStatusRow(
            id = R.drawable.pressure,
            iconDescription = "Pressure Icon",
            text = "${weatherItem.pressure} psi"
        )

        WeatherStatusRow(
            id = R.drawable.wind,
            iconDescription = "Wind Icon",
            text = "${weatherItem.speed} ${
                when(unitType) {
                    UnitType.METRIC -> WIND_SPEED_METRIC
                    else -> WIND_SPEED_IMPERIAL
                }
            }"
        )
    }
}

@Composable
fun SunRiseAndSetRow(weatherItem: MainWeatherItem) {
    WeatherInfoRow {
        WeatherStatusRow(
            id = R.drawable.sunrise,
            iconDescription = "Sunrise Icon",
            text = weatherItem.formattedSunrise
        )

        WeatherStatusRow(
            id = R.drawable.sunset,
            iconDescription = "Sunset Icon",
            text = weatherItem.formattedSunset,
            startIcon = false
        )
    }
}

@Composable
fun WeatherOfWeek(mainWeather: MainWeather) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = WeatherBackground,
                shape = RoundedCornerShape(20.dp)
            )
    ) {

        items(mainWeather.list) {
            WeatherOfWeekRow(mainWeatherItem = it)
        }
    }
}

@Composable
fun WeatherOfWeekRow(mainWeatherItem: MainWeatherItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 45.dp,
                    topEnd = 10.dp,
                    bottomStart = 45.dp,
                    bottomEnd = 45.dp
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 45.dp,
                    topEnd = 10.dp,
                    bottomStart = 45.dp,
                    bottomEnd = 45.dp
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            mainWeatherItem.run {
                Text(
                    text = formattedDayOfWeek,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
                )

                WeatherStateImage(imageUrl = toMainItemIconUrl())

                WeatherTagText(text = toMainItemWeatherDescription())

                MaxMinTemp(
                    maxTemperature = toMainItemWeatherMaxTemperature(),
                    minTemperature = toMainItemWeatherMinTemperature()
                )
            }
        }
    }
}