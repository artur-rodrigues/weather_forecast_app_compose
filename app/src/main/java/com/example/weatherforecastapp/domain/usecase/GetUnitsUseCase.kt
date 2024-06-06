package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Unit
import com.example.weatherforecastapp.domain.model.converter.convertToUnit
import com.example.weatherforecastapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnitsUseCase @Inject constructor(
    private val repository: WeatherRepository
) : RoomGetUseCase<String, Unit>() {

    override fun get(): Flow<Result<List<Unit>>> {
        return internalGet(repository.getUnits()) {
            it.convertToUnit()
        }
    }

    override suspend fun find(key: String): Result<Unit> {
        return internalFind(repository.getUnitById(key)) {
            it.convertToUnit()
        }
    }
}
