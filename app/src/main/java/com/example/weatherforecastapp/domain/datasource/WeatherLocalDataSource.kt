package com.example.weatherforecastapp.domain.datasource

import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.data.model.UnitDao
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {

    fun getFavorites(): Flow<Result<List<FavoriteDao>>>

    suspend fun addFavorite(favorite: FavoriteDao)

    suspend fun updateFavorite(favorite: FavoriteDao)

    suspend fun deleteAllFavorites()

    suspend fun deleteFavorite(favorite: FavoriteDao)

    suspend fun getFavoriteById(city: String): Result<FavoriteDao>


    fun getUnits(): Flow<Result<List<UnitDao>>>

    suspend fun addUnit(unit: UnitDao): Long

    suspend fun updateUnit(unit: UnitDao)

    suspend fun deleteAllUnits()

    suspend fun deleteUnit(unit: UnitDao)

    suspend fun getUnitById(unit: String): Result<UnitDao>
}