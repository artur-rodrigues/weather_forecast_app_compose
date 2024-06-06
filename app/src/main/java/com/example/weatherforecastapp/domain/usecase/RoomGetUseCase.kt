package com.example.weatherforecastapp.domain.usecase

import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.util.BothOnAction
import com.example.weatherforecastapp.util.getConvertedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class RoomGetUseCase<KEY, OUT> {

    abstract fun get(): Flow<Result<List<OUT>>>
    abstract suspend fun find(key: KEY): Result<OUT>

    fun <IN> internalGet(
        flow: Flow<Result<List<IN>>>,
        action: BothOnAction<IN, OUT>
    ): Flow<Result<List<OUT>>> {
        return flow.map { ret ->
            ret.getConvertedResult { list ->
                list.map { dao ->
                    action(dao)
                }
            }
        }
    }

    fun <IN> internalFind(
        result: Result<IN>,
        actionConvert: BothOnAction<IN, OUT>
    ): Result<OUT> {
        return result.getConvertedResult(actionConvert)
    }
}