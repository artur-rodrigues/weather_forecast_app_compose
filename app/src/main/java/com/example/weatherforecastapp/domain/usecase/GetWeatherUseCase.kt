package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.MainWeather
import com.example.weatherforecastapp.domain.model.converter.getMainWeatherSuccessResult
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend fun get(cityQuery: String, unit: String): Result<MainWeather> {
        return when (val ret = repository.getWeather(cityQuery, unit)) {
            is Result.Success -> ret.data.getMainWeatherSuccessResult()
            is Result.Error -> ret
            is Result.Loading -> ret
        }
    }
}