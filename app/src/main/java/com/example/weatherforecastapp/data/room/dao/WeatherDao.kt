package com.example.weatherforecastapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.data.model.UnitDao
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM fav_tbl")
    fun getFavorites(): Flow<List<FavoriteDao>>

    @Query("SELECT * FROM fav_tbl WHERE city = :city")
    suspend fun getFavoriteById(city: String): FavoriteDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteDao)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: FavoriteDao)

    @Query("DELETE FROM fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteDao)



    @Query("SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<UnitDao>>

    @Query("SELECT * FROM settings_tbl WHERE unit = :unit")
    suspend fun getUnitById(unit: String): UnitDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUnit(unit: UnitDao): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(unit: UnitDao)

    @Query("DELETE FROM settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: UnitDao)
}