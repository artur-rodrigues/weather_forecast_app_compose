package com.example.weatherforecastapp.domain.model

class MainWeather(
    val cod: String,
    val cnt: Int,
    val city: City,
    val list: List<MainWeatherItem>
)