package com.example.weatherforecastapp.domain.model.converter

import com.example.weatherforecastapp.data.model.UnitDao
import com.example.weatherforecastapp.domain.model.Unit

fun UnitDao.convertToUnit(): Unit {
    return Unit(
        unit
    )
}

fun Unit.convertToUnitDao(): UnitDao {
    return UnitDao(
        unit
    )
}