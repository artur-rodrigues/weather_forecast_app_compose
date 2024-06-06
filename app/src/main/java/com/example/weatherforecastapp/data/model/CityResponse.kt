package com.example.weatherforecastapp.data.model

data class CityResponse(
    val coord: CoordResponse,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)