package com.example.weatherforecastapp.domain.datasource

import com.example.weatherforecastapp.data.model.MainWeatherResponse
import com.example.weatherforecastapp.data.model.Result

interface WeatherRemoteDataSource {

    suspend fun getWeather(cityQuery: String, unit: String): Result<MainWeatherResponse>
}