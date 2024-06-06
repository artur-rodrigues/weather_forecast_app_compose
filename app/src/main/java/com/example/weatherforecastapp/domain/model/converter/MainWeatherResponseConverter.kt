package com.example.weatherforecastapp.domain.model.converter

import com.example.weatherforecastapp.data.model.CityResponse
import com.example.weatherforecastapp.data.model.MainWeatherItemResponse
import com.example.weatherforecastapp.data.model.MainWeatherResponse
import com.example.weatherforecastapp.data.model.TempResponse
import com.example.weatherforecastapp.data.model.WeatherResponse
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.City
import com.example.weatherforecastapp.domain.model.MainWeather
import com.example.weatherforecastapp.domain.model.MainWeatherItem
import com.example.weatherforecastapp.domain.model.Temp
import com.example.weatherforecastapp.domain.model.Weather
import com.example.weatherforecastapp.util.convertToDayOfWeek
import com.example.weatherforecastapp.util.convertToFormattedDate
import com.example.weatherforecastapp.util.convertToFormattedDateTime

fun MainWeatherResponse.getMainWeatherSuccessResult(): Result.Success<MainWeather> {
    return Result.Success(convertToMainWeather())
}

fun MainWeatherResponse.convertToMainWeather(): MainWeather {
    return MainWeather(
        cod,
        cnt,
        city.convertToCity(),
        list.map { it.convertToMainWeather() }
    )
}

fun CityResponse.convertToCity(): City {
    return City(name, country)
}

fun MainWeatherItemResponse.convertToMainWeather(): MainWeatherItem {
    return MainWeatherItem(
        dt.convertToFormattedDate(),
        dt.convertToDayOfWeek(),
        humidity,
        pressure,
        speed,
        sunrise.convertToFormattedDateTime(),
        sunset.convertToFormattedDateTime(),
        temp.convertToTemp(),
        weather.map { it.convertToWeather() }
    )
}

fun TempResponse.convertToTemp(): Temp {
    return Temp(
        day,
        max,
        min
    )
}

fun WeatherResponse.convertToWeather(): Weather {
    return Weather(
        main,
        description,
        icon
    )
}