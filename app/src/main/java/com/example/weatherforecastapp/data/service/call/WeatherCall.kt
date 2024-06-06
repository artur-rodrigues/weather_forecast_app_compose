package com.example.weatherforecastapp.data.service.call

import com.example.weatherforecastapp.data.model.ApiErrorResponse
import com.example.weatherforecastapp.data.model.Result
import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class WeatherCall<T>(
    private val delegate: Call<T>,
) : Call<Result<T>> {

    override fun clone(): Call<Result<T>> = WeatherCall(delegate.clone())

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled()  = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = Timeout().timeout(30, TimeUnit.SECONDS)

    override fun enqueue(callback: Callback<Result<T>>) {
        return delegate.enqueue(getCallBack(callback))
    }

    private fun getCallBack(callback: Callback<Result<T>>) = object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {

            val result = if(response.isSuccessful) {
                when(val body = response.body()) {
                    null -> Result.Error(Exception("Response body is null"))
                    else -> Result.Success(body)
                }
            } else {
                try {
                    val error = Gson().fromJson(response.errorBody()?.string(), ApiErrorResponse::class.java)

                    Result.Error(Exception("Api Error: ${error.message}"), error)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.Error(Exception("Error converting Error Body", e))
                }

            }

            sendResponse(result)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            sendResponse(Result.Error(Exception(t)))
        }

        private fun sendResponse(result: Result<T>) {
            callback.onResponse(this@WeatherCall, Response.success(result))
        }
    }
}