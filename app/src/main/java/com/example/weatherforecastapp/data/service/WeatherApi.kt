package com.example.weatherforecastapp.data.service

import com.example.weatherforecastapp.data.model.MainWeatherResponse
import com.example.weatherforecastapp.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") unit: String
    ): Result<MainWeatherResponse>
}