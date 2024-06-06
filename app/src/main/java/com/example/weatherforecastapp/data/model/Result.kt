package com.example.weatherforecastapp.data.model

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    data object Loading : Result<Nothing>()
    class Error(val exception: Exception, val apiErrorResponse: ApiErrorResponse? = null) : Result<Nothing>()
}