package com.example.weatherforecastapp.util

import com.example.weatherforecastapp.BuildConfig
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.City
import com.example.weatherforecastapp.domain.model.MainWeather
import com.example.weatherforecastapp.domain.model.MainWeatherItem
import com.example.weatherforecastapp.domain.model.Temp
import com.example.weatherforecastapp.domain.model.Weather
import com.example.weatherforecastapp.util.Constants.DATE_PATTERN
import com.example.weatherforecastapp.util.Constants.DATE_TIME_PATTERN
import com.example.weatherforecastapp.util.Constants.DAY_OF_WEEK_PATTERN
import com.example.weatherforecastapp.util.Constants.DEGREE_PATTER
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun MainWeather.toMainIconUrl(): String {
    return list[0].weather[0].toIconUrl()
}

fun MainWeather.toMainDate(): String {
    return list[0].formattedDate
}

fun MainWeather.toMainTempDay(): String {
    return list[0].temp.day.toFormattedTemperature()
}

fun MainWeather.toMainCondition(): String {
    return list[0].weather[0].condition
}

fun MainWeatherItem.toMainItemIconUrl(): String {
    return weather[0].toIconUrl()
}

fun MainWeatherItem.toMainItemWeatherDescription(): String {
    return weather[0].description
}

fun MainWeatherItem.toMainItemWeatherMaxTemperature(): String {
    return temp.max.toFormattedTemperature()
}

fun MainWeatherItem.toMainItemWeatherMinTemperature(): String {
    return temp.min.toFormattedTemperature()
}

fun Weather.toIconUrl(): String {
    return BuildConfig.IMAGE_URL.format(icon)
}

fun Double.toFormattedTemperature(pattern: String = DEGREE_PATTER): String {
    return String.format(pattern, this)
}

fun Long.convertToFormattedDate(): String {
    return convertToDate(DATE_PATTERN).replaceFirstChar { it.titlecase() }
}

fun Long.convertToFormattedDateTime(): String {
    return convertToDate(DATE_TIME_PATTERN)
}

fun Long.convertToDayOfWeek(): String {
    return convertToDate(DAY_OF_WEEK_PATTERN).replaceFirstChar { it.titlecase() }
}

fun Long.convertToDate(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this * 1000))
}

fun <IN, OUT> Result<IN>.getConvertedResult(execute: (IN) -> OUT): Result<OUT> {
    return when(this) {
        is Result.Success<IN> -> Result.Success(execute(data))
        is Result.Error -> Result.Error(exception, apiErrorResponse)
        Result.Loading -> Result.Loading
    }
}

fun getDummyMainWeather(): MainWeather {
    return MainWeather(
        "200",
        7,
        City("Seattle", "USA"),
        listOf(
            MainWeatherItem(
                "Ter, jan 20",
                "Ter",
                100,
                100,
                10.10,
                "07:00:AM",
                "19:00:PM",
                Temp(55.55, 70.0, 20.0),
                listOf(
                    Weather(
                        "Rain",
                        "Heavy Rain",
                        "04d"
                    )
                )
            )
        )
    )
}