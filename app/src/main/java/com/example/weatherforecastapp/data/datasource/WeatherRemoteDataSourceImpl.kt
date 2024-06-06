package com.example.weatherforecastapp.data.datasource

import com.example.weatherforecastapp.data.model.MainWeatherResponse
import com.example.weatherforecastapp.data.service.WeatherApi
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.datasource.WeatherRemoteDataSource
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRemoteDataSource {

    override suspend fun getWeather(cityQuery: String, unit: String): Result<MainWeatherResponse> {
        return weatherApi.getWeather(cityQuery, unit)
    }

}