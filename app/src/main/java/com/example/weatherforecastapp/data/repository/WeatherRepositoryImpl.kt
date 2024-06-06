package com.example.weatherforecastapp.data.repository

import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.data.model.MainWeatherResponse
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.data.model.UnitDao
import com.example.weatherforecastapp.domain.datasource.WeatherLocalDataSource
import com.example.weatherforecastapp.domain.datasource.WeatherRemoteDataSource
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun getWeather(cityQuery: String, unit: String): Result<MainWeatherResponse> {
        return remoteDataSource.getWeather(cityQuery,  unit)
    }



    override fun getFavorites(): Flow<Result<List<FavoriteDao>>> {
        return localDataSource.getFavorites()
    }

    override suspend fun addFavorite(favorite: FavoriteDao) {
        localDataSource.addFavorite(favorite)
    }

    override suspend fun updateFavorite(favorite: FavoriteDao) {
        localDataSource.updateFavorite(favorite)
    }

    override suspend fun deleteAllFavorites() {
        localDataSource.deleteAllFavorites()
    }

    override suspend fun deleteFavorite(favorite: FavoriteDao) {
        localDataSource.deleteFavorite(favorite)
    }

    override suspend fun getFavoriteById(city: String): Result<FavoriteDao> {
        return localDataSource.getFavoriteById(city)
    }



    override fun getUnits(): Flow<Result<List<UnitDao>>> {
        return localDataSource.getUnits()
    }

    override suspend fun addUnit(unit: UnitDao): Long {
        return localDataSource.addUnit(unit)
    }

    override suspend fun updateUnit(unit: UnitDao) {
        localDataSource.updateUnit(unit)
    }

    override suspend fun deleteAllUnits() {
        localDataSource.deleteAllUnits()
    }

    override suspend fun deleteUnit(unit: UnitDao) {
        localDataSource.deleteUnit(unit)
    }

    override suspend fun getUnitById(unit: String): Result<UnitDao> {
        return localDataSource.getUnitById(unit)
    }

}