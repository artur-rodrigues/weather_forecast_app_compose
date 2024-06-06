package com.example.weatherforecastapp.data.model

data class TempResponse(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)