package com.example.weatherforecastapp.data.datasource

import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.data.model.UnitDao
import com.example.weatherforecastapp.data.room.dao.WeatherDao
import com.example.weatherforecastapp.domain.datasource.WeatherLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override fun getFavorites(): Flow<Result<List<FavoriteDao>>> {
        return weatherDao.getFavorites().map {
            Result.Success(it)
        }
    }

    override suspend fun addFavorite(favorite: FavoriteDao) {
        weatherDao.addFavorite(favorite)
    }

    override suspend fun updateFavorite(favorite: FavoriteDao) {
        weatherDao.updateFavorite(favorite)
    }

    override suspend fun deleteAllFavorites() {
        weatherDao.deleteAllUnits()
    }

    override suspend fun deleteFavorite(favorite: FavoriteDao) {
        weatherDao.deleteFavorite(favorite)
    }

    override suspend fun getFavoriteById(city: String): Result<FavoriteDao> {
        return Result.Success(weatherDao.getFavoriteById(city))
    }

    override fun getUnits(): Flow<Result<List<UnitDao>>> {
        return weatherDao.getUnits().map {
            Result.Success(it)
        }
    }

    override suspend fun addUnit(unit: UnitDao): Long {
        return weatherDao.addUnit(unit)
    }

    override suspend fun updateUnit(unit: UnitDao) {
        weatherDao.updateUnits(unit)
    }

    override suspend fun deleteAllUnits() {
        weatherDao.deleteAllUnits()
    }

    override suspend fun deleteUnit(unit: UnitDao) {
        weatherDao.deleteUnit(unit)
    }

    override suspend fun getUnitById(unit: String): Result<UnitDao> {
        return Result.Success(weatherDao.getUnitById(unit))
    }

}