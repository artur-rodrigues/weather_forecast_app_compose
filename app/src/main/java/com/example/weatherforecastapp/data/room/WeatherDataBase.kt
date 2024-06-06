package com.example.weatherforecastapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecastapp.data.model.FavoriteDao
import com.example.weatherforecastapp.data.model.UnitDao
import com.example.weatherforecastapp.data.room.dao.WeatherDao

@Database(entities = [FavoriteDao::class, UnitDao::class], version = 2, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}