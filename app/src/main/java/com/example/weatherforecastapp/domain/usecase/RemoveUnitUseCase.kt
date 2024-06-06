package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.domain.model.Unit
import com.example.weatherforecastapp.domain.model.converter.convertToUnitDao
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import javax.inject.Inject

class RemoveUnitUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend fun remove(unit: Unit) {
        repository.deleteUnit(unit.convertToUnitDao())
    }

    suspend fun removeAll() {
        repository.deleteAllUnits()
    }
}
