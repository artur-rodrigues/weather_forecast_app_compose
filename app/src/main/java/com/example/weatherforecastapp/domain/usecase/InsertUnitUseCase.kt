package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.domain.model.Unit
import com.example.weatherforecastapp.domain.model.converter.convertToUnitDao
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertUnitUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend fun add(unit: Unit): Long {
        return repository.addUnit(unit.convertToUnitDao())
    }

    suspend fun update(unit: Unit) {
        repository.updateUnit(unit.convertToUnitDao())
    }
}
