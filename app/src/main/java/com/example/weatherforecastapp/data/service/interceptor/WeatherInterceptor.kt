package com.example.weatherforecastapp.data.service.interceptor

import com.example.weatherforecastapp.BuildConfig
import com.example.weatherforecastapp.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WeatherInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().run {
            val newUrl = url.newBuilder()
                .addQueryParameter(Constants.APP_ID, BuildConfig.API_KEY)
                .build()

            newBuilder().url(newUrl).build()
        }

        return chain.proceed(newRequest)
    }
}