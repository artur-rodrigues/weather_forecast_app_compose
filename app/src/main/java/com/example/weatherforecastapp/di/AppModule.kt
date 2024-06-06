package com.example.weatherforecastapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecastapp.BuildConfig
import com.example.weatherforecastapp.data.datasource.WeatherLocalDataSourceImpl
import com.example.weatherforecastapp.data.datasource.WeatherRemoteDataSourceImpl
import com.example.weatherforecastapp.data.repository.WeatherRepositoryImpl
import com.example.weatherforecastapp.data.room.WeatherDataBase
import com.example.weatherforecastapp.data.room.dao.WeatherDao
import com.example.weatherforecastapp.data.service.WeatherApi
import com.example.weatherforecastapp.data.service.call.WeatherResponseAdapterFactory
import com.example.weatherforecastapp.data.service.interceptor.WeatherInterceptor
import com.example.weatherforecastapp.domain.datasource.WeatherLocalDataSource
import com.example.weatherforecastapp.domain.datasource.WeatherRemoteDataSource
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppProviderModule {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao {
        return weatherDataBase.weatherDao()
    }

    @Provides
    fun provideWeatherDataBase(@ApplicationContext context: Context): WeatherDataBase {
        return Room
            .databaseBuilder(context, WeatherDataBase::class.java, "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(WeatherResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHttpClient(weatherInterceptor: WeatherInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(weatherInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if(BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppBindingModule {

    @Binds
    abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindWeatherRemoteDataSource(repository: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource

    @Binds
    abstract fun bindWeatherLocalDataSource(repository: WeatherLocalDataSourceImpl): WeatherLocalDataSource
}