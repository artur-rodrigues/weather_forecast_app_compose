package com.example.weatherforecastapp.domain.model

class MainWeatherItem(
    val formattedDate: String,
    val formattedDayOfWeek: String,
    val humidity: Int,
    val pressure: Int,
    val speed: Double,
    val formattedSunrise: String,
    val formattedSunset: String,
    val temp: Temp,
    val weather: List<Weather>
)
