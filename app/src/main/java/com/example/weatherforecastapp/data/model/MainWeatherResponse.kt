package com.example.weatherforecastapp.data.model

data class MainWeatherResponse(
    val city: CityResponse,
    val cnt: Int,
    val cod: String,
    val list: List<MainWeatherItemResponse>,
    val message: Double
)