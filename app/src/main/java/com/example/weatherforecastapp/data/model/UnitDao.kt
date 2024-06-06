package com.example.weatherforecastapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class UnitDao(

    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit: String
)
