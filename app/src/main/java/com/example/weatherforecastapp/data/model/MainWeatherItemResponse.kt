package com.example.weatherforecastapp.data.model

import retrofit2.http.Field

data class MainWeatherItemResponse (
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    @Field("feels_like")
    val feelsLike: FeelsLikeResponse,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val speed: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: TempResponse,
    val weather: List<WeatherResponse>
)